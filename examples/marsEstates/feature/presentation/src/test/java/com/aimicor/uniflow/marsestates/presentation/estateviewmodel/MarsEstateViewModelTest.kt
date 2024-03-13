package com.aimicor.uniflow.marsestates.presentation.estateviewmodel

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
import com.aimicor.test.TestCoroutineRule
import com.aimicor.uniflow.marsestates.domain.MarsEstate
import com.aimicor.uniflow.marsestates.domain.MarsEstateUseCase
import com.aimicor.uniflow.marsestates.ui.content.MarsEstateEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MarsEstateViewModelTest {

    @get:Rule
    val rule = TestCoroutineRule()

    private val marsEstateUseCase = mockk<MarsEstateUseCase>()
    private val viewModel by lazy { MarsEstateViewModel(marsEstateUseCase) }

    @Test
    fun `WHEN back button event THEN effect is sent to view`() = runTest {
        // Given
        coEvery { marsEstateUseCase() } returns Result.success(listOf(mockk<MarsEstate>()))

        // When
        viewModel.handleEvent(MarsEstateEvent.OnClose)

        // Then
        viewModel.sideEffect.test {
            assertTrue(awaitItem() is MarsEstateSideEffect.Close)
        }
    }

    @Test
    fun `GIVEN list of estates WHEN started THEN list of estates is not empty`() = runTest {
        // Given
        val results = listOf<MarsEstate>(mockk(), mockk(), mockk())
        coEvery { marsEstateUseCase() } returns Result.success(results)

        // When
        viewModel

        // Then
        viewModel.uiState.test {
            assertEquals(results, awaitItem().estates)
        }
    }
}