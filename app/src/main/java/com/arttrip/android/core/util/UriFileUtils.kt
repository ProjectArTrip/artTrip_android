package com.arttrip.android.core.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

/**
 * Content Uri를 cache 파일로 복사해 File로 변환한다.
 * (PhotoPicker/SAF Uri는 실제 파일 경로를 못 얻는 경우가 많아서 "복사")
 */
fun Uri.copyToCacheFile(
    context: Context,
    subDir: String = "uploads",
    filePrefix: String = "profile_",
): File? =
    runCatching {
        val resolver = context.contentResolver

        val ext =
            run {
                val mime = resolver.getType(this@copyToCacheFile)
                val fromMime = mime?.substringAfter("/")?.takeIf { it.isNotBlank() }?.let { ".$it" }
                fromMime ?: guessExtensionFromDisplayName(context) ?: ".jpg"
            }

        val dir = File(context.cacheDir, subDir).apply { mkdirs() }
        val outFile = File(dir, "$filePrefix${System.currentTimeMillis()}$ext")

        resolver.openInputStream(this@copyToCacheFile)?.use { input ->
            outFile.outputStream().use { output -> input.copyTo(output) }
        } ?: return null

        outFile
    }.getOrNull()

private fun Uri.guessExtensionFromDisplayName(context: Context): String? {
    val resolver = context.contentResolver
    val cursor = resolver.query(this, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null) ?: return null
    cursor.use {
        if (!it.moveToFirst()) return null
        val name = it.getString(0) ?: return null
        val dot = name.lastIndexOf('.')
        if (dot <= 0 || dot == name.lastIndex) return null
        return name.substring(dot) // ".jpg"
    }
}
