package com.aimicor.uniflow.marsestates.presentation.navviewmodel

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

import com.aimicor.uniflow.Uniflow
import com.aimicor.uniflow.UniflowViewModel
import com.aimicor.uniflow.marsestates.ui.scaffold.buyHeading
import com.aimicor.uniflow.marsestates.ui.scaffold.rentHeading
import com.aimicor.uniflow.marsestates.ui.content.buy
import com.aimicor.uniflow.marsestates.ui.content.rent
import com.aimicor.uniflow.marsestates.ui.scaffold.NavEvent
import com.aimicor.uniflow.marsestates.ui.scaffold.NavUiState

interface NavUniflow : Uniflow<NavEvent, NavUiState, NavSideEffect>

open class NavViewModel : NavUniflow,
    UniflowViewModel<NavEvent, NavUiState, NavSideEffect>(
        NavUiState(
            topBarHeading = buyHeading,
            currentRoute = buy.route
        )
    ) {

    override fun handleEvent(event: NavEvent) {
        when (event) {
            NavEvent.OnCloseClicked -> sendSideEffect { NavSideEffect.Close }
            is NavEvent.UpdateScaffold -> updateScaffold(event.route)
            is NavEvent.GoToScreen -> sendSideEffect { NavSideEffect.GoToScreen(event.screenKey) }
        }
    }

    private fun updateScaffold(route: String?) {
        when (route) {
            buy.route -> setUiState {
                NavUiState(
                    topBarHeading = buyHeading,
                    currentRoute = buy.route
                )
            }

            rent.route -> setUiState {
                NavUiState(
                    topBarHeading = rentHeading,
                    currentRoute = rent.route
                )
            }
        }
    }
}