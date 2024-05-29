package com.aimicor.uniflow.marsestates.domain

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

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MarsEstateUseCaseTest {

    private val repo = mockk<MarsRepository>()
    private val sut: MarsEstateUseCase by lazy {
        MarsEstateUseCase.buy(repo)
    }

    @Test
    fun `GIVEN two types WHEN invoked THEN only buy returned`() = runTest {
        // Given
        val expected = MarsEstate(456, isBuy = true, "")
        coEvery { repo.fetchMarsData() } returns Result.success(
            listOf(
                MarsEstate(123, isBuy = false, ""),
                expected
            )
        )

        // When
        sut()

            // Then
            .onSuccess { result ->
                assertEquals(1, result.size)
                assertEquals(expected, result.first())
            }

            // Else
            .onFailure { throw it }
    }
}