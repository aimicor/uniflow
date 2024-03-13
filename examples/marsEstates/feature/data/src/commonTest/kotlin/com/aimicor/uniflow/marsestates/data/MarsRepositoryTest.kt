package com.aimicor.uniflow.marsestates.data

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

import com.aimicor.uniflow.marsestates.domain.MarsEstate
import com.aimicor.uniflow.marsestates.domain.MarsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MarsRepositoryTest {

    private var jsonString = ""

    private val mockEngine = MockEngine { request ->
        /* test request object to match expected api call */
        respond(
            content = ByteReadChannel(jsonString),
            status = HttpStatusCode.OK,
            headers = headersOf(ContentType, "application/json")
        )
    }

    private val ktorClient = HttpClient(mockEngine) {
        install(ContentNegotiation) { json() }
    }

    private val sut: MarsRepository = MarsRepo.impl(ktorClient)

    @Test
    fun `GIVEN a single estate WHEN fetched THEN data parsed`() = runTest {
        // Given
        val price = 450000
        val imgSrc = "some/url"
        jsonString = """
            [
                {
                    "price":$price,
                    "id":"424905",
                    "type":"buy",
                    "img_src":"$imgSrc"
                }
            ]"""

        // When
        val result: Result<List<MarsEstate>> = sut.fetchMarsData()

        // Then
        assertTrue(result.isSuccess)
        result.getOrThrow().first().let {
            assertEquals(price, it.price)
            assertTrue(it.isBuy)
            assertEquals(imgSrc, it.imgSrc)
        }

        result.onSuccess {  }.onFailure {  }
    }
}