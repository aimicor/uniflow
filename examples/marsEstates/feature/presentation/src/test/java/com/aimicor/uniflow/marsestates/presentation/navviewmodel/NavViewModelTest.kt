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

import app.cash.turbine.test
import com.aimicor.navcompose.common.navComposableSpec
import com.aimicor.test.TestCoroutineRule
import com.aimicor.uniflow.marsestates.ui.scaffold.buyHeading
import com.aimicor.uniflow.marsestates.ui.scaffold.rentHeading
import com.aimicor.uniflow.marsestates.ui.content.buy
import com.aimicor.uniflow.marsestates.ui.content.rent
import com.aimicor.uniflow.marsestates.ui.scaffold.NavEvent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class NavViewModelTest {

    @get:Rule
    val rule = TestCoroutineRule()

    private val viewModel by lazy { NavViewModel() }

    @Test
    fun `WHEN back button event THEN effect is sent to view`() = runTest {
        // When
        viewModel.handleEvent(NavEvent.OnCloseClicked)

        // Then
        viewModel.sideEffect.test {
            Assert.assertTrue(awaitItem() is NavSideEffect.Close)
        }
    }


    @Test
    fun `WHEN goto screen event THEN effect is sent to view`() = runTest {
        // Given
        val screen = navComposableSpec()

        // When
        viewModel.handleEvent(NavEvent.GoToScreen(screen))

        // Then
        viewModel.sideEffect.test {
            assertEquals(screen, (awaitItem() as NavSideEffect.GoToScreen).to)
        }
    }

    @Test
    fun `WHEN update scaffold with buy THEN state updated`() = runTest {
        // When
        viewModel.handleEvent(NavEvent.UpdateScaffold(buy.route))

        // Then
        viewModel.uiState.test {
            awaitItem().apply {
                assertEquals(buy.route, currentRoute)
                assertEquals(buyHeading, topBarHeading)
            }
        }
    }

    @Test
    fun `WHEN update scaffold with rent THEN state updated`() = runTest {
        // When
        viewModel.handleEvent(NavEvent.UpdateScaffold(rent.route))

        // Then
        viewModel.uiState.test {
            awaitItem().apply {
                assertEquals(rent.route, currentRoute)
                assertEquals(rentHeading, topBarHeading)
            }
        }
    }
}