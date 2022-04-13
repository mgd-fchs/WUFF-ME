The `at.tugraz.software22.ui` package contains all classes of the *UI layer*.

The role of the UI is to display the application data on the screen and also to serve as the 
primary point of user interaction. Whenever the data changes, either due to user interaction 
(like pressing a button) or external input (like a network response), the UI should update to 
reflect those changes. Effectively, the UI is a visual representation of the application state as
retrieved from the data layer (via `ViewModels`).

For further details see: https://developer.android.com/jetpack/guide/ui-layer

> According to the grading sheet, this package must contain all user interface components
> (`Activities`, `Fragments`, `Views`, ...).
> `ViewModels` must be located in the `viewmodel` subpackage.