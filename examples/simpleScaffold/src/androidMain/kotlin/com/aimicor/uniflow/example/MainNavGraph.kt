package com.aimicor.uniflow.example

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aimicor.navcompose.android.goBack
import com.aimicor.navcompose.android.navigate
import com.aimicor.uniflow.collectWithLifecycle
import com.aimicor.uniflow.example.navviewmodel.NavEvent
import com.aimicor.uniflow.example.navviewmodel.NavSideEffect
import com.aimicor.uniflow.example.navviewmodel.NavUniflow

@Composable
internal fun MainNavGraph(
    navViewModel: NavUniflow = NavUniflow()
) {
    val state by navViewModel.uiState.collectAsStateWithLifecycle()
    BackHandler { navViewModel.handleEvent(NavEvent.OnCloseClicked) }

    val fullScreenNavController = rememberNavController()
    val scaffoldNavController = rememberNavController()

    navViewModel.handleEvent(
        NavEvent.UpdateScaffold(scaffoldNavController.collectRouteAsState)
    )
    NavGraph(
        scaffoldNavController, fullScreenNavController,
        state, navViewModel::handleEvent
    )

    navViewModel.sideEffect.collectWithLifecycle { effect ->
        when (effect) {
            NavSideEffect.Close -> scaffoldNavController.goBack()
            is NavSideEffect.GoToScaffold -> scaffoldNavController.navigate(effect.to)
            is NavSideEffect.GoToFullScreen -> fullScreenNavController.navigate(effect.to)
            is NavSideEffect.JumpToScaffold -> fullScreenNavController.apply {
                popBackStack(
                    graph.findStartDestination().id,
                    inclusive = false
                )
                scaffoldNavController.navigate(effect.to)
            }
        }
    }
}

private val NavHostController.collectRouteAsState: String?
    @Composable
    get() =
        currentBackStackEntryFlow
            .collectAsStateWithLifecycle(currentBackStackEntry)
            .value?.destination?.route

