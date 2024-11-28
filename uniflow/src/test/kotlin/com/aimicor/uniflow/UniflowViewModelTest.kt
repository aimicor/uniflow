package com.aimicor.uniflow

import app.cash.turbine.test
import com.aimicor.test.TestCoroutineRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class UniflowViewModelTest {

    @get:Rule
    val rule = TestCoroutineRule()

    private val sut by lazy { UniflowViewModelImpl() }

    @Test
    fun `Given handleEvent called Then a new state with different values should be set`() = runTest {
        sut.handleEvent(EventDummy.ChangeName)
        sut.handleEvent(EventDummy.ChangeAddress)
        sut.uiState.test {
            awaitItem().apply {
                assertEquals("Stuart", name)
                assertEquals("Flat 2", address)
            }
        }
    }

    @Test
    fun `Given sendAction called Then the action has to be sent`() = runTest {
        sut.handleEvent(EventDummy.SendAction)
        sut.sideEffect.test {
            assertNotNull(awaitItem())
        }
    }

    private class UniflowViewModelImpl :
        UniflowViewModel<EventDummy, UiStateFake, ActionDummy>(UiStateFake()) {
        override fun handleEvent(event: EventDummy) {
            when (event) {
                is EventDummy.ChangeName -> {
                    setUiState {
                        copy(name = "Stuart")
                    }
                }

                is EventDummy.ChangeAddress -> {
                    setUiState {
                        copy(address = "Flat 2")
                    }
                }

                is EventDummy.SendAction -> {
                    sendSideEffect { ActionDummy }
                }
            }
        }
    }

    private sealed class EventDummy {
        data object ChangeName : EventDummy()
        data object ChangeAddress : EventDummy()
        data object SendAction : EventDummy()
    }

    private data class UiStateFake(val name: String = "Dominic", val address: String = "Flat 1")

    private object ActionDummy
}
