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
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import java.io.IOException

const val marsUrl = "https://mars.udacity.com/realestate"

interface MarsRepo : MarsRepository {

    companion object {
        fun impl(
            ktorClient: HttpClient = HttpClient {
                install(ContentNegotiation) { json() }
            }
        ): MarsRepository = MarsRepositoryImpl(ktorClient)
    }
}

private class MarsRepositoryImpl(
    private val ktorClient: HttpClient
) : MarsRepository {

    // TODO test-drive this cache
    private var marsCache: List<MarsEstate>? = null

    override suspend fun fetchMarsData(
        isStale: Boolean
    ): Result<List<MarsEstate>> = try {
        val response: HttpResponse = ktorClient.get(marsUrl)

        if (response.status.isSuccess()) {
            Result.success(
                response.body<List<MarsData>>().map {
                    MarsEstate(it.price, it.type == "buy", it.imgSrc)
                }
            )
        } else {
            Result.failure(IOException(response.status.description))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
