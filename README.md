# UniFlow

[![license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/aimicor/uniflow/blob/master/LICENSE)  
[![Maven Central](https://img.shields.io/maven-central/v/com.aimicor/uniflow-android  
)](https://central.sonatype.com/artifact/com.aimicor/uniflow-android)  

This simple Android library implements the uni-directional data flow pattern, [suggested by Google](https://developer.android.com/jetpack/compose/architecture), that is ideal for use with a `ViewModel` and Jetpack Compose:

<img src="https://github.com/aimicor/uniflow/blob/main/state-unidirectional-flow.png" alt="drawing" width="170"/>

(Source: [developer.android.com](https://developer.android.com/jetpack/compose/architecture))

Similar to Model-View-Intent (MVI), the concept aims to cut down the proliferation of the methods and states required for the `ViewModel` and the `Composable` to interact. Ultimately (in theory), any `Composable` UI function should take at most two parameters:
``` kotlin
@Composable  
fun MyUiContent(  
    state: MyUiState,  
    event: (MyEvent) -> Unit  
) { /*...*/ }
```
Where `MyUiState`  (`state`) is an object of a data class containing all of the information needed by the `Composable` function to render the UI and `MyEvent` of `event` is typically a sealed class containing all of the objects from events in the UI, such as button-clicks, that are sent to the `ViewModel` via a lambda. `state` is controlled upstream by a `StateFlow`.

It turns out, however, that `state` and `event` are insufficient for full UI control. There are typically side effects coming from the `ViewModel`, such as navigating to a different screen, that require a separate "channel". We can't reuse `state` for this purpose because it is has to be a `StateFlow` to trigger an immediate UI draw. Instead it needs to be a `Flow`, something that can be listened to without necessarily doing anything at all until called upon. With this in mind, we need to update the diagram above:

<img src="https://github.com/aimicor/uniflow/blob/main/state-udf-update.png" alt="drawing" width="300"/>

The Controller can be an `Activity`, `Fragment` or another `Composable`. The same sealed class for the event can be used by both the UI and the Controller to send events to the `ViewModel`.

This is best explained with an example...
## Installation
Include the library(ies)
```kotlin dsl  
dependencies {
    implementation("com.aimicor:uniflow-android:[maven-central version]") 
    // ... or version catalog equivalent 
    implementation("androidx.lifecycle:lifecycle-runtime-compose:[latest compatible version]")
    // ... optional but recommended
}  
```  
## Usage
### Define some events, states and side-effects
```kotlin  
sealed class MyEvent {  
    data object OnCloseClicked : MyEvent()
    data class OnDataSent(val data: Stuff) : MyEvent()  
    // more events...
}

sealed class MyUiState {
    data object Loading: MyUiState()
    data class Failed(val msg: String): MyUiState()
    data class Success(val stuffToDisplay: MyDisplayData): MyUiState()
}

sealed class MySideEffect {  
    data object Close : MySideEffect()
    // more effects...
}
```  
### Define a Uniflow interface
```kotlin  
interface MyUniflow  
    : Uniflow<MyEvent, MyUiState, MySideEffect>
```  
### Define a Uniflow `ViewModel`
```kotlin  
class MyViewModel : MyUniflow, UniflowViewModel<MyEvent, MyUiState, MySideEffect>(
    initialUiState = MyUiState()
) {  
    override fun handleEvent(event: MyEvent) {
        when (event) {  
            MyEvent.OnCloseClicked -> sendSideEffect { MySideEffect.Close }  
            is MyEvent.OnDataSent -> setUiState { 
                copy(stuffToDisplay = getOtherStuffFrom(event.data))
            }
            // more events...
        }
    }
}
```  
### Define a content Composable
```kotlin  
@Composable  
fun MyUiContent(  
    state: MyUiState,  
    event: (MyEvent) -> Unit  
) {
    when (state) {
        is MyUiState.Loading -> { /* show a spinner */ }
        is MyUiState.Failed -> { /* show fail message */ }
        is MyUiState.Success -> { /* render my MyDisplayData */ }
    }
    // ...
    Button(onClick = { event(MyEvent.OnDataSent(someStuff)) } )
    // ...
}

```  
### Define a controller (another Composable in this case)
```kotlin  
@Composable  
fun MyController(  
    uniflow: MyUniflow = viewModel<MyViewModel>()
) {  
    val state by uniflow.uiState.collectAsStateWithLifecycle()  
    BackHandler { uniflow.handleEvent(MyEvent.OnCloseClicked) }  
    MyUiContent(state, uniflow::handleEvent)

    val localContext = LocalContext.current
    uniflow.sideEffect.collectWithLifecycle { effect ->  
        when (effect) {  
            MySideEffect.Close -> (localContext as? Activity)?.finish()
            // other side effects...
        }
    }
}
```
### Finally, invoke the Controller
```kotlin 
class MainActivity : ComponentActivity() {  

    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContent { MyController() }  
    }  
}
```
This pattern encourages the centralisation of all of the UI-related logic to the `ViewModel`, using the decoupled elements of the state, event and side-effect, facilitating easier test-driven development of said `ViewModel`.
