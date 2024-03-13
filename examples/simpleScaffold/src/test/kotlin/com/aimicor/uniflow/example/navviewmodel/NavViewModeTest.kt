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

import app.cash.turbine.test
import com.aimicor.navcompose.common.navComposableSpec
import com.aimicor.test.TestCoroutineRule
import com.aimicor.uniflow.example.screens.screenInScaffold1
import com.aimicor.uniflow.example.screens.screenInScaffold2
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
            assertTrue(awaitItem() is NavSideEffect.Close)
        }
    }

    @Test
    fun `WHEN goto scaffold event THEN effect is sent to view`() = runTest {
        // Given
        val screen = navComposableSpec()

        // When
        viewModel.handleEvent(NavEvent.GoToScaffold(screen))

        // Then
        viewModel.sideEffect.test {
            assertEquals(screen, (awaitItem() as NavSideEffect.GoToScaffold).to)
        }
    }

    @Test
    fun `WHEN goto fullscreen event THEN effect is sent to view`() = runTest {
        // Given
        val screen = navComposableSpec()

        // When
        viewModel.handleEvent(NavEvent.GoToFullScreen(screen))

        // Then
        viewModel.sideEffect.test {
            assertEquals(screen, (awaitItem() as NavSideEffect.GoToFullScreen).to)
        }
    }

    @Test
    fun `WHEN jumpto event THEN effect is sent to view`() = runTest {
        // Given
        val screen = navComposableSpec()

        // When
        viewModel.handleEvent(NavEvent.JumpToScaffold(screen))

        // Then
        viewModel.sideEffect.test {
            assertEquals(screen, (awaitItem() as NavSideEffect.JumpToScaffold).to)
        }
    }

    @Test
    fun `WHEN update scaffold with scaffold 1 THEN state updated`() = runTest {
        // When
        viewModel.handleEvent(NavEvent.UpdateScaffold(screenInScaffold1.route))

        // Then
        viewModel.uiState.test {
            awaitItem().apply {
                assertEquals(screenInScaffold1.route, currentRoute)
                assertEquals(NavUniflow.scaffold1, topBarHeading)
            }
        }
    }

    @Test
    fun `WHEN update scaffold with scaffold 2 THEN state updated`() = runTest {
        // When
        viewModel.handleEvent(NavEvent.UpdateScaffold(screenInScaffold2.route))

        // Then
        viewModel.uiState.test {
            awaitItem().apply {
                assertEquals(screenInScaffold2.route, currentRoute)
                assertEquals(NavUniflow.scaffold2, topBarHeading)
            }
        }
    }
}