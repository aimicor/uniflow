package com.aimicor.uniflow

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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base class  for Uni-Directional Flow ViewModels
 * References:
 *
 * https://developer.android.com/jetpack/compose/architecture
 * https://developer.android.com/jetpack/compose/side-effects
 * https://developer.android.com/topic/architecture/ui-layer#why-use-udf
 * https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d
 */
abstract class UniflowViewModel<EVENT, STATE, EFFECT>(
    private val initialUiState: STATE
) :  Uniflow<EVENT, STATE, EFFECT>, ViewModel() {

    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialUiState) }
    override val uiState by lazy { _uiState.asStateFlow() }

    private val _sideEffect: Channel<EFFECT> = Channel()
    override val sideEffect by lazy { _sideEffect.receiveAsFlow() }

    protected fun setUiState(reduce: STATE.() -> STATE) {
        _uiState.value = _uiState.value.reduce()
    }

    protected fun sendSideEffect(builder: () -> EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(builder())
        }
    }
}
