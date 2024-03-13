package com.aimicor.uniflow.example.scaffold

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

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.aimicor.uniflow.example.navviewmodel.NavEvent
import com.aimicor.uniflow.example.screens.screenInScaffold1
import com.aimicor.uniflow.example.screens.screenInScaffold2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun Fab(
    destination: String?,
    event: (NavEvent) -> Unit,
    snackState: SnackbarHostState
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                snackState.showSnackbar(
                    message = "Snack Bar",
                    actionLabel = "Navigate",
                    duration = SnackbarDuration.Indefinite
                ).let { result -> switchScreens(destination, event, result) }
            }
        },
        content = { Icon(Icons.Default.Add, "") }
    )
}

private fun switchScreens(
    destination: String?,
    event: (NavEvent) -> Unit,
    result: SnackbarResult
) {
    when (result) {
        SnackbarResult.Dismissed -> {}
        SnackbarResult.ActionPerformed -> event(
            NavEvent.GoToScaffold(
                if (destination == screenInScaffold1.route) screenInScaffold2
                else screenInScaffold1
            )
        )
    }
}