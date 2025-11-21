package com.arttrip.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arttrip.android.core.navigation.AppNavHost
import com.arttrip.android.core.navigation.bottomNavItems
import com.arttrip.android.core.ui.component.bottomNav.AppBottomNavBar
import com.arttrip.android.core.ui.theme.ArtTripTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtTripTheme {
                MainScaffold(Modifier)
            }
        }
    }
}

@Composable
fun MainScaffold(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                //     .navigationBarsPadding()
                contentAlignment = Alignment.TopCenter,
            ) {
                AppBottomNavBar(
                    items = bottomNavItems,
                    selectedRoute = currentRoute,
                    onItemSelected = { item ->
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}

@Preview(
    name = "MainActivity Preview",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewMainActivity() {
    ArtTripTheme {
        MainScaffold(Modifier)
    }
}
