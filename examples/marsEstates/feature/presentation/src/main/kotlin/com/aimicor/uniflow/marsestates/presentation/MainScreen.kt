package com.aimicor.uniflow.marsestates.presentation

/*
MIT License

Copyright (c) 2024 Aimicor Ltd.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aimicor.navcompose.android.NavHost
import com.aimicor.navcompose.android.composable
import com.aimicor.navcompose.android.goBack
import com.aimicor.navcompose.android.navigate
import com.aimicor.navcompose.common.NavComposableSpec
import com.aimicor.uniflow.collectWithLifecycle
import com.aimicor.uniflow.marsestates.ui.scaffold.Scaffolder
import com.aimicor.uniflow.marsestates.presentation.estateviewmodel.MarsEstateUniflow
import com.aimicor.uniflow.marsestates.presentation.navviewmodel.NavSideEffect
import com.aimicor.uniflow.marsestates.presentation.navviewmodel.NavUniflow
import com.aimicor.uniflow.marsestates.ui.content.buy
import com.aimicor.uniflow.marsestates.ui.content.rent
import com.aimicor.uniflow.marsestates.ui.scaffold.NavEvent

@Composable
fun MainContent(
    buyUniflow: MarsEstateUniflow,
    rentUniflow: MarsEstateUniflow,
    navUniflow: NavUniflow
) {
    val navController = rememberNavController()
    navUniflow.handleEvent(NavEvent.UpdateScaffold(navController.collectRouteAsState))
    BackHandler { navUniflow.handleEvent(NavEvent.OnCloseClicked) }

    navUniflow.sideEffect.collectWithLifecycle { effect ->
        when (effect) {
            NavSideEffect.Close -> navController.goBack()
            is NavSideEffect.GoToScreen -> navController.navigate(effect.to)
        }
    }

    val state by navUniflow.uiState.collectAsStateWithLifecycle()
    Scaffolder(state, navUniflow::handleEvent) {
        NavHost(
            navController,
            startDestination = buy
        ) {
            composableAnim(buy) { MarsEstateScreen(buyUniflow) }
            composableAnim(rent) { MarsEstateScreen(rentUniflow) }
        }
    }
}

private fun NavGraphBuilder.composableAnim(
    composableSpec: NavComposableSpec,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        composableSpec,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popExitTransition = { ExitTransition.None },
        content = content
    )
}

private val NavHostController.collectRouteAsState: String?
    @Composable
    get() =
        currentBackStackEntryFlow
            .collectAsStateWithLifecycle(currentBackStackEntry)
            .value?.destination?.route
