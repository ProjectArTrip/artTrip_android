package com.arttrip.android.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.roundToInt

fun File.toMultipartPart(
    fieldName: String,
    contentType: MediaType = "image/*".toMediaType(),
): MultipartBody.Part {
    val body = asRequestBody(contentType)
    return MultipartBody.Part.createFormData(
        name = fieldName,
        filename = name,
        body = body,
    )
}

/**
 * 업로드 제한 용량에 맞추기 위해 이미지 파일을 리사이즈 및 압축.
 *
 * 처리 방식
 * - 원본 이미지의 긴 변이 [maxLongSide]를 초과하면 먼저 리사이즈한다.
 * - 이후 JPEG 품질을 단계적으로 낮추며 결과 파일 크기를 [targetMaxBytes] 이하로 맞춘다.
 * - 압축 결과는 원본 파일을 덮어쓰지 않고 별도의 새 파일로 생성한다.
 *
 * 사용 목적
 * - 서버의 multipart 업로드 용량 제한(2MB) 대응
 * - 모바일 네트워크 환경에서 불필요하게 큰 이미지 업로드 방지
 *
 * 주의 사항
 * - 결과 파일은 JPEG로 저장되므로 투명도(alpha)는 유지되지 않음.
 */
fun File.compressImageForUpload(
    targetMaxBytes: Long = 1_500_000L,
    maxLongSide: Int = 1280,
    minQuality: Int = 55,
    qualityStep: Int = 5,
): File {
    if (!exists()) return this
    if (length() <= targetMaxBytes) return this

    val decodedBitmap = BitmapFactory.decodeFile(absolutePath) ?: return this
    val rotatedBitmap = decodedBitmap.rotateByExifIfNeeded(absolutePath)
    if (rotatedBitmap !== decodedBitmap) {
        decodedBitmap.recycle()
    }

    val resizedBitmap = rotatedBitmap.resizeIfNeeded(maxLongSide)
    if (resizedBitmap !== rotatedBitmap) {
        rotatedBitmap.recycle()
    }

    var quality = 90
    var compressedBytes: ByteArray

    do {
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        compressedBytes = outputStream.toByteArray()
        outputStream.close()

        if (compressedBytes.size <= targetMaxBytes || quality <= minQuality) {
            break
        }
        quality -= qualityStep
    } while (true)

    val compressedFile =
        File(
            parentFile,
            "${nameWithoutExtension}_compressed.jpg",
        )

    FileOutputStream(compressedFile).use { fos ->
        fos.write(compressedBytes)
        fos.flush()
    }

    resizedBitmap.recycle()
    return compressedFile
}

private fun Bitmap.resizeIfNeeded(maxLongSide: Int): Bitmap {
    val srcWidth = width
    val srcHeight = height
    val longSide = max(srcWidth, srcHeight)

    if (longSide <= maxLongSide) return this

    val ratio = maxLongSide.toFloat() / longSide.toFloat()
    val dstWidth = (srcWidth * ratio).roundToInt()
    val dstHeight = (srcHeight * ratio).roundToInt()

    return this.scale(dstWidth, dstHeight)
}

private fun Bitmap.rotateByExifIfNeeded(filePath: String): Bitmap {
    val exif = ExifInterface(filePath)
    val orientation =
        exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL,
        )

    val matrix =
        Matrix().apply {
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> preScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> preScale(1f, -1f)
                else -> return this@rotateByExifIfNeeded
            }
        }

    return Bitmap.createBitmap(
        this,
        0,
        0,
        width,
        height,
        matrix,
        true,
    )
}
