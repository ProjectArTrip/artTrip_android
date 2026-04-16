package com.arttrip.app.presentation.exhibition.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ExhibitionMapTab(latitude: Double?, longitude: Double?) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(220.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.Gray50),
        contentAlignment = Alignment.Center,
    ) {
        if (latitude != null && longitude != null) {
            val position = remember(latitude, longitude) { LatLng(latitude, longitude) }
            val cameraPositionState = rememberCameraPositionState {
                this.position = CameraPosition.fromLatLngZoom(position, 15f)
            }

            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = false,
                    scrollGesturesEnabled = false,
                    tiltGesturesEnabled = false,
                    rotationGesturesEnabled = false,
                    scrollGesturesEnabledDuringRotateOrZoom = false,
                    mapToolbarEnabled = false,
                    compassEnabled = false,
                    myLocationButtonEnabled = false,
                ),
                properties = MapProperties(isMyLocationEnabled = false),
            ) {
                Marker(state = remember { MarkerState(position = position) })
            }
        } else {
            Text(
                text = "위치 정보가 없습니다",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
        }
    }
}
