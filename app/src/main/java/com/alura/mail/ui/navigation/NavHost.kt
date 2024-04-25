package com.alura.mail.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alura.mail.ui.components.DefaultAppBar
import com.alura.mail.ui.components.HomeAppBar
import com.alura.mail.ui.components.HomeBottomBar
import com.alura.mail.ui.components.HomeFAB
import com.alura.mail.ui.home.HomeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val state by homeViewModel.uiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()

    val expandedFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination by remember {
        derivedStateOf {
            homeViewModel.changeCurrentDestination(
                navBackStackEntry?.destination?.route ?: emailListRoute
            )
            navBackStackEntry?.destination
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Crossfade(
                state.showEmailsList,
                animationSpec = tween(300)
            ) { showEmailsList ->
                if (showEmailsList) {
                    HomeAppBar(scrollBehavior)
                } else {
                    DefaultAppBar(
                        title = stringResource(homeViewModel.getAppBarTitle()),
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        },
        floatingActionButton = {
            if (state.showEmailsList) {
                HomeFAB(expandedFab)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination?.route != contentEmailFullPath,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(500)
                )
            ) {
                HomeBottomBar(
                    currentTab = currentDestination?.route ?: emailListRoute,
                    onItemSelected = { route ->
                        navController.navigateDirect(route)
                    }
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = emailListRoute,
                modifier = modifier,
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) },
                popEnterTransition = { fadeIn(tween(200)) },
                popExitTransition = { fadeOut(tween(200)) },
            ) {
                emailsListScreen(
                    listState = listState,
                    onOpenEmail = { email ->
                        navController.navigateToContentEmailScreen(email.id)
                        homeViewModel.setSelectedEmail(email)
                    },
                )
                contentEmailScreen()
                translateSettingsScreen()
            }
        }
    }
}

fun NavHostController.navigateDirect(rota: String) = this.navigate(rota) {
    popUpTo(this@navigateDirect.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
