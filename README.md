# Coroutines Channel

### Pros
- Data is emitted only once, unlike StateFlow and LiveData, where last data will be redelivered to a new observer.
- Even if there are multiple observers, only one observer will receive the data.
- All events are buffered until someone observes them.

### Cons
- Data sources will exist in a suspended state until someone requests/observes the data. This makes channels hot.
