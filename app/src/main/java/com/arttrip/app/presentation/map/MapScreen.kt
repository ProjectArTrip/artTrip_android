package com.arttrip.app.presentation.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.arttrip.app.R
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.core.ui.component.list.ExhibitionListItem
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.util.noRippleClickable
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.arttrip.app.presentation.map.contract.MapIntent
import com.arttrip.app.presentation.map.contract.MapState
import com.google.android.gms.maps.model.CameraPosition
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
    bookmarked: Map<Int, Boolean>,
) {
    val cameraPositionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(state.cameraLatLng, state.cameraZoom)
        }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val pos = cameraPositionState.position
            onIntent(MapIntent.OnCameraMoved(latLng = pos.target, zoom = pos.zoom))
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
    val scope = rememberCoroutineScope()

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
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
        if (!state.hasCenteredOnLocation && state.currentLocation != null) {
            cameraPositionState.animate(
                update =
                    com.google.android.gms.maps.CameraUpdateFactory
                        .newLatLngZoom(state.currentLocation, 15f),
            )
            onIntent(MapIntent.OnLocationCentered)
        }
    }

    LaunchedEffect(isExpanded) {
        if (isExpanded) clusterExhibits.refresh()
    }

    BottomSheetScaffold(
        modifier =
            modifier
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
                bookmarked = bookmarked,
                onExhibitionClick = { id -> onIntent(MapIntent.ExhibitionClicked(id)) },
                onLikeClick = { id -> onIntent(MapIntent.LikeClicked(id)) },
            )
        },
    ) {
        MapContent(
            cameraPositionState = cameraPositionState,
            markers = state.markers,
            selectedCountry = state.selectedCountry,
            onCountrySelected = { onIntent(MapIntent.OnCountrySelected(it)) },
            onMyLocationClick = {
                scope.launch {
                    state.currentLocation?.let { location ->
                        cameraPositionState.animate(
                            update =
                                com.google.android.gms.maps.CameraUpdateFactory
                                    .newLatLngZoom(location, 15f),
                        )
                    }
                }
            },
            onClusterClick = { cluster ->
                onIntent(
                    MapIntent.OnClusterClicked(
                        count = cluster.size,
                        ids = cluster.items.map { it.id.toInt() },
                    ),
                )
                scope.launch {
                    cameraPositionState.animate(
                        update =
                            com.google.android.gms.maps.CameraUpdateFactory
                                .newLatLng(cluster.position),
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
    selectedCountry: ForeignCountry?,
    onCountrySelected: (ForeignCountry?) -> Unit,
    onClusterClick: (Cluster<ExhibitionMarker>) -> Unit,
    onCameraIdle: (Int, List<Int>) -> Unit,
    onMyLocationClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var dropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val countries = ForeignCountry.entries.filter { it != ForeignCountry.Entire }

    LaunchedEffect(cameraPositionState.isMoving, markers) {
        if (!cameraPositionState.isMoving) {
            val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds ?: return@LaunchedEffect
            val visibleMarkers = markers.filter { bounds.contains(it.latLng) }
            onCameraIdle(visibleMarkers.size, visibleMarkers.map { it.id.toInt() })
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings =
                MapUiSettings(
                    zoomControlsEnabled = false,
                ),
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
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .shadow(6.dp, RoundedCornerShape(8.dp))
                        .border(width = 1.dp, color = AppColor.Gray100, shape = RoundedCornerShape(size = 8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = AppColor.Gray0),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .noRippleClickable { dropdownExpanded = !dropdownExpanded }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = selectedCountry?.label ?: "국가를 선택해주세요.",
                        style = AppTextStyle.Body01Bold,
                        color = if (selectedCountry != null) AppColor.TextPrimary else AppColor.TextTertiary,
                    )
                    Icon(
                        painter =
                            painterResource(
                                id = if (dropdownExpanded) R.drawable.ic_up_24 else R.drawable.ic_down_24,
                            ),
                        contentDescription = null,
                        tint = AppColor.Gray900,
                    )
                }
                AnimatedVisibility(visible = dropdownExpanded) {
                    Column {
                        HorizontalDivider(color = AppColor.Gray100)
                        countries.forEach { country ->
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .noRippleClickable {
                                            onCountrySelected(country)
                                            dropdownExpanded = false
                                            country.latLng?.let { latLng ->
                                                scope.launch {
                                                    cameraPositionState.animate(
                                                        update =
                                                            com.google.android.gms.maps.CameraUpdateFactory
                                                                .newLatLngZoom(latLng, 8f),
                                                    )
                                                }
                                            }
                                        }.padding(all = 16.dp),
                            ) {
                                Text(
                                    text = country.label,
                                    style = AppTextStyle.Body01Light,
                                    color = AppColor.TextPrimary,
                                )
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = onMyLocationClick,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-24).dp, y = (-88).dp)
                    .size(40.dp),
            elevation =
                FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
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
            .background(AppColor.Gray100, RoundedCornerShape(10.dp)),
    )
}

@Composable
fun BottomSheetContent(
    innerPadding: PaddingValues,
    isExpanded: Boolean,
    clusterCount: Int,
    clusterExhibits: LazyPagingItems<Exhibition>,
    bookmarked: Map<Int, Boolean>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    val bottomNavHeight = innerPadding.calculateBottomPadding()
    val topMargin = 88.dp
    val dragHandleHeight = 24.dp
    val expandedSheetHeight = screenHeight - statusBarHeight - bottomNavHeight - topMargin - dragHandleHeight

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(expandedSheetHeight)
                .background(color = AppColor.Gray0),
    ) {
        if (isExpanded) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
            ) {
                Spacer(
                    modifier =
                        Modifier
                            .height(16.dp),
                )
                Row {
                    Text(
                        text = "전시",
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPrimary,
                    )
                    Spacer(
                        modifier =
                            Modifier
                                .width(8.dp),
                    )
                    Text(
                        text = clusterCount.toString(),
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPoint,
                    )
                    Text(
                        text = "건",
                        style = AppTextStyle.Headline,
                        color = AppColor.TextPrimary,
                    )
                }
                Spacer(
                    modifier =
                        Modifier
                            .height(8.dp),
                )
                if (clusterExhibits.itemCount == 0) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(
                            modifier =
                                Modifier
                                    .height(56.dp),
                        )
                        Text(
                            text =
                                "해당 위치에 전시가 없습니다.\n" +
                                    "다른 위치를 검색해보세요.",
                            style = AppTextStyle.Body01Regular,
                            color = AppColor.TextTertiary,
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                                    isLiked = bookmarked[exhibition.id] ?: exhibition.isBookmarked,
                                    onLikeClick = { onLikeClick(exhibition.id) },
                                    onItemClick = { onExhibitionClick(exhibition.id) },
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
                color = AppColor.TextPoint,
            )
        }
    }
}

@Composable
fun PlaceCluster(cluster: Cluster<ExhibitionMarker>) {
    Box(
        modifier =
            Modifier
                .size(66.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .background(AppColor.Gray900.copy(alpha = 0.8f), CircleShape),
        )
        Text(
            text = cluster.size.toString(),
            style = AppTextStyle.Title01Bold,
            color = AppColor.TextWhite,
        )
    }
}

@Composable
fun PlaceMarker() {
    Box(
        modifier =
            Modifier
                .size(16.dp)
                .background(AppColor.Primary300, CircleShape),
    )
}
