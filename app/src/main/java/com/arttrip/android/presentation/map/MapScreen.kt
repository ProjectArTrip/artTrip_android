package com.arttrip.android.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.arttrip.android.presentation.map.contract.MapIntent
import com.arttrip.android.presentation.map.contract.MapState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    state: MapState,
    onIntent: (MapIntent) -> Unit,
) {
    val seoul = LatLng(37.5665, 126.9780)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoul, 13f)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = modifier
            .padding(bottom = innerPadding.calculateBottomPadding()),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = AppColor.Gray0,
        sheetDragHandle = {
            BottomSheetDragHandle()
        },
        sheetPeekHeight = 64.dp,
        sheetContent = {
            BottomSheetContent(
                innerPadding = innerPadding,
                isExpanded = isExpanded,
                clusterCount = state.selectedClusterCount,
            )
        }
    ) {
        MapContent(
            cameraPositionState = cameraPositionState,
            markers = state.markers,
            onClusterClick = { cluster ->
                onIntent(MapIntent.OnClusterClicked(count = cluster.size))
                scope.launch { scaffoldState.bottomSheetState.expand() }
            },
            onCameraIdle = { visibleCount ->
                onIntent(MapIntent.OnCameraIdle(visibleCount = visibleCount))
            },
        )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun MapContent(
    cameraPositionState: com.google.maps.android.compose.CameraPositionState,
    markers: List<ExhibitionMarker>,
    onClusterClick: (Cluster<ExhibitionMarker>) -> Unit,
    onCameraIdle: (Int) -> Unit,
) {
    LaunchedEffect(cameraPositionState.isMoving, markers) {
        if (!cameraPositionState.isMoving) {
            val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
            val count = bounds?.let { b -> markers.count { b.contains(it.latLng) } } ?: 0
            onCameraIdle(count)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            )
        ) {
            Clustering<ExhibitionMarker>(
                items = markers,
                onClusterClick = { cluster ->
                    onClusterClick(cluster)
                    true
                },
                clusterContent = { cluster ->
                    PlaceCluster(cluster = cluster)
                },
                clusterItemContent = {
                    PlaceMarker()
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = AppColor.Primary300)
            )
        }
    }
}

@Composable
fun BottomSheetDragHandle() {
    Box(
        Modifier
            .padding(vertical = 10.dp)
            .size(width = 32.dp, height = 4.dp)
            .background(AppColor.Gray100, RoundedCornerShape(10.dp))
    )
}

@Composable
fun BottomSheetContent(
    innerPadding: PaddingValues,
    isExpanded: Boolean,
    clusterCount: Int,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val statusBarHeight = WindowInsets.statusBars.getTop(density)
    val dragHandleHeight = 24.dp
    val expandedSheetHeight = with(density) {
        windowInfo.containerSize.height.toDp() - statusBarHeight.toDp() - innerPadding.calculateBottomPadding() - 88.dp - dragHandleHeight
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(expandedSheetHeight)
            .background(color = AppColor.Gray0)
    ) {
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                Row {
                    Text(
                        text = "전시",
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPrimary
                    )
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                    )
                    Text(
                        text = clusterCount.toString(),
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPoint
                    )
                    Text(
                        text = "건",
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPrimary
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    repeat(100) {
                        Text("Item $it")
                    }
                }
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "전시 리스트 확인하기",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPoint
            )
        }
    }
}

@Composable
fun PlaceCluster(cluster: Cluster<ExhibitionMarker>) {
    Box(
        modifier = Modifier
            .size(66.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(AppColor.Gray900.copy(alpha = 0.8f), CircleShape)
        )
        Text(
            text = cluster.size.toString(),
            style = AppTextStyle.Title01Bold,
            color = AppColor.TextWhite
        )
    }
}

@Composable
fun PlaceMarker() {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(AppColor.Primary300, CircleShape)
    )
}
