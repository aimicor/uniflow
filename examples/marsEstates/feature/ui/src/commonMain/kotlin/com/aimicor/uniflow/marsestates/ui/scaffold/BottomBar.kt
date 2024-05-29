package com.aimicor.uniflow.marsestates.ui.scaffold

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

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import com.aimicor.uniflow.marsestates.ui.content.buy
import com.aimicor.uniflow.marsestates.ui.content.rent

@Composable
internal fun BottomBar(
    destination: String?,
    event: (NavEvent) -> Unit,
) {
    BottomAppBar(
        content = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, buyHeading) },
                    label = { Text(buyHeading) },
                    selected = destination == buy.route,
                    onClick = { event(NavEvent.GoToScreen(buy)) }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Favorite, rentHeading) },
                    label = { Text(rentHeading) },
                    selected = destination == rent.route,
                    onClick = { event(NavEvent.GoToScreen(rent)) }
                )
            }
        }
    )
}