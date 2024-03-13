package com.aimicor.uniflow.example.navviewmodel

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

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aimicor.uniflow.Uniflow
import com.aimicor.uniflow.UniflowViewModel
import com.aimicor.uniflow.example.screens.screenInScaffold1
import com.aimicor.uniflow.example.screens.screenInScaffold2

internal interface NavUniflow
    : Uniflow<NavEvent, NavUiState, NavSideEffect> {

    companion object {
        const val scaffold1 = "Scaffold 1"
        const val scaffold2 = "Scaffold 2"

        @Composable
        operator fun invoke(): NavUniflow = viewModel<NavViewModel>()
    }
}

internal open class NavViewModel : NavUniflow,
    UniflowViewModel<NavEvent, NavUiState, NavSideEffect>(
        NavUiState(
            topBarHeading = NavUniflow.scaffold1,
            currentRoute = screenInScaffold1.route
        )
    ) {

    override fun handleEvent(event: NavEvent) {
        when (event) {
            NavEvent.OnCloseClicked -> sendSideEffect {
                NavSideEffect.Close
            }

            is NavEvent.GoToScaffold -> sendSideEffect {
                NavSideEffect.GoToScaffold(event.screenKey)
            }

            is NavEvent.GoToFullScreen -> sendSideEffect {
                NavSideEffect.GoToFullScreen(event.screenKey)
            }

            is NavEvent.JumpToScaffold -> sendSideEffect {
                NavSideEffect.JumpToScaffold(event.screenKey)
            }

            is NavEvent.UpdateScaffold -> updateScaffold(event.route)
        }
    }

    private fun updateScaffold(route: String?) {
        when (route) {
            screenInScaffold1.route -> setUiState {
                NavUiState(
                    topBarHeading = NavUniflow.scaffold1,
                    currentRoute = screenInScaffold1.route
                )
            }

            screenInScaffold2.route -> setUiState {
                NavUiState(
                    topBarHeading = NavUniflow.scaffold2,
                    currentRoute = screenInScaffold2.route
                )
            }
        }
    }
}