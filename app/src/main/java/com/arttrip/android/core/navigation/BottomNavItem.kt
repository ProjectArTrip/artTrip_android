package com.arttrip.android.core.navigation

import androidx.annotation.DrawableRes
import com.arttrip.android.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @param:DrawableRes val iconRes: Int,
) {
    object Home : BottomNavItem("home", "홈", R.drawable.ic_home_32)

    object Map : BottomNavItem("map", "지도", R.drawable.ic_map_32)

    object Stamp : BottomNavItem("stamp", "스탬프", R.drawable.ic_stamp_20)

    object Bookmark : BottomNavItem("bookmark", "즐겨찾기", R.drawable.ic_bookmark_32)

    object MyPage : BottomNavItem("mypage", "My", R.drawable.ic_my_32)
}

val bottomNavItems =
    listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Stamp,
        BottomNavItem.Bookmark,
        BottomNavItem.MyPage,
    )
