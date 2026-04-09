package com.arttrip.android.presentation.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.list.ExhibitionListItem
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.Exhibition
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
    clusterExhibits: LazyPagingItems<Exhibition>,
    onIntent: (MapIntent) -> Unit,
) {
    val seoul = LatLng(37.5665, 126.9780)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoul, 15f)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
    val scope = rememberCoroutineScope()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        if (permissions.values.any { it }) {
            onIntent(MapIntent.OnLocationPermissionGranted)
        } else {
            onIntent(MapIntent.OnLocationPermissionDenied)
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
        )
    }

    LaunchedEffect(state.currentLocation) {
        state.currentLocation?.let { location ->
            cameraPositionState.move(
                update = com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(location, 15f),
            )
        }
    }

    LaunchedEffect(isExpanded) {
        if (isExpanded) clusterExhibits.refresh()
    }

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
                clusterExhibits = clusterExhibits,
            )
        }
    ) {
        MapContent(
            cameraPositionState = cameraPositionState,
            markers = state.markers,
            onMyLocationClick = {
                scope.launch {
                    state.currentLocation?.let { location ->
                        cameraPositionState.animate(
                            update = com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(location, 15f),
                        )
                    }
                }
            },
            onClusterClick = { cluster ->
                onIntent(
                    MapIntent.OnClusterClicked(
                        count = cluster.size,
                        ids = cluster.items.map { it.id.toInt() },
                    )
                )
                scope.launch {
                    cameraPositionState.animate(
                        update = com.google.android.gms.maps.CameraUpdateFactory.newLatLng(cluster.position),
                    )
                    scaffoldState.bottomSheetState.expand()
                }
            },
            onCameraIdle = { visibleCount, ids ->
                onIntent(MapIntent.OnCameraIdle(visibleCount = visibleCount, ids = ids))
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
    onCameraIdle: (Int, List<Int>) -> Unit,
    onMyLocationClick: () -> Unit,
) {
    LaunchedEffect(cameraPositionState.isMoving, markers) {
        if (!cameraPositionState.isMoving) {
            val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
            val visibleMarkers = bounds?.let { b -> markers.filter { b.contains(it.latLng) } } ?: emptyList()
            onCameraIdle(visibleMarkers.size, visibleMarkers.map { it.id.toInt() })
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
            Clustering(
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
        FloatingActionButton(
            onClick = onMyLocationClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-24).dp, y = (-88).dp)
                .size(40.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                    spotColor = Color(0x29000000),
                    ambientColor = Color(0x29000000),
                ),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
            ),
            containerColor = AppColor.Gray0,
            shape = CircleShape,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location_24),
                contentDescription = "내 위치 이동",
                tint = AppColor.Gray900,
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
    clusterExhibits: LazyPagingItems<Exhibition>,
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
                if (clusterExhibits.itemCount == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(56.dp)
                        )
                        Text(
                            text = "해당 위치에 전시가 없습니다.\n" +
                                    "다른 위치를 검색해보세요.",
                            style = AppTextStyle.Body01Regular,
                            color = AppColor.TextTertiary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            count = clusterExhibits.itemCount,
                            key = clusterExhibits.itemKey { it.id },
                        ) { index ->
                            val exhibition = clusterExhibits[index]
                            if (exhibition != null) {
                                ExhibitionListItem(
                                    posterUrl = exhibition.posterUrl,
                                    location = null,
                                    title = exhibition.title,
                                    hallName = exhibition.hallName,
                                    period = exhibition.period,
                                    status = exhibition.status,
                                    isLiked = exhibition.isBookmarked,
                                    onLikeClick = {},
                                    onItemClick = {}
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
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
