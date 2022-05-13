package de.tvsmiles.app.presentation.composables

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.applovin.mediation.ads.MaxAdView
import de.tvsmiles.app.R
import de.tvsmiles.app.utils.BottomNavItem

@Composable
fun MainBottomNavigation(
    navController: NavController
) {

    val items = listOf(
        BottomNavItem.Banner,
        BottomNavItem.Interstitial,
        BottomNavItem.Rewarded
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color(R.color.black)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.screenRoute,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title, fontSize = 12.sp)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenView(
    onLoadBanner: () -> Unit,
    onShowBanner: () -> Unit,
    bannerAdView: MaxAdView?,
    onLoadInterstitial: () -> Unit,
    onShowInterstitial: () -> Unit,
    onLoadRewarded: () -> Unit,
    onShowRewarded: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                navController = navController
            )
        }
    ) {
        NavigationGraph(
            navController = navController,
            bannerAdView = bannerAdView,
            onLoadBanner = onLoadBanner,
            onShowBanner = onShowBanner,
            onLoadInterstitial = onLoadInterstitial,
            onShowInterstitial = onShowInterstitial,
            onLoadRewarded = onLoadRewarded,
            onShowRewarded = onShowRewarded
        )
    }
}