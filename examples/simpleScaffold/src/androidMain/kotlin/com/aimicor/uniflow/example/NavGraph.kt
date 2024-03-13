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

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.aimicor.navcompose.android.NavHost
import com.aimicor.navcompose.android.composable
import com.aimicor.navcompose.common.NavComposableSpec
import com.aimicor.uniflow.example.navviewmodel.NavEvent
import com.aimicor.uniflow.example.navviewmodel.NavUiState
import com.aimicor.uniflow.example.scaffold.Scaffolder
import com.aimicor.uniflow.example.screens.Fullscreen1
import com.aimicor.uniflow.example.screens.Fullscreen2
import com.aimicor.uniflow.example.screens.ScreenInScaffold1
import com.aimicor.uniflow.example.screens.ScreenInScaffold2
import com.aimicor.uniflow.example.screens.fullscreen1
import com.aimicor.uniflow.example.screens.fullscreen2
import com.aimicor.uniflow.example.screens.scaffoldComposable
import com.aimicor.uniflow.example.screens.screenInScaffold1
import com.aimicor.uniflow.example.screens.screenInScaffold2

@Composable
internal fun NavGraph(
    scaffoldNavController: NavHostController,
    fullScreenNavController: NavHostController,
    state: NavUiState,
    event: (NavEvent) -> Unit
) {
    NavHost( // outer NavHost
        fullScreenNavController,
        startDestination = scaffoldComposable
    ) {
        composable(fullscreen1) { Fullscreen1(event) }
        composable(fullscreen2) { Fullscreen2(event) }
        composable(scaffoldComposable) {
            Scaffolder(state, event) {
                NavHost( // inner NavHost
                    scaffoldNavController,
                    startDestination = screenInScaffold1
                ) {
                    composableAnim(screenInScaffold1) { ScreenInScaffold1(event) }
                    composableAnim(screenInScaffold2) { ScreenInScaffold2(event) }
                }
            }
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
