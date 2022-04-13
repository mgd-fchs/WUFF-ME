The `at.tugraz.software22.data` package contains all classes of the *data layer*. 

The data layer contains application data and business logic. The business logic is what gives 
value to your app — it is made of real-world business rules that determine how application data 
must be created, stored, and changed.

For further details see: https://developer.android.com/jetpack/guide/data-layer

> According to the grading sheet, this package must contain at least all entities (e.g., `Sprint`) 
> and repositories (e.g., `SprintRepository`).

Applications with complex business logic have an additional *domain layer* sitting in between
the *data* and the *UI layer*. The domain layer is optional because not all apps will have these 
requirements. You should only use it when needed — for example, to handle complexity or 
favor reusability. 

For further details see: https://developer.android.com/jetpack/guide/domain-layer