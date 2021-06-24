# Coroutines Channel

### Pro

Data is emitted only once, unlike StateFlow and LiveData, where last data will be redelivered to a new observer.

### Con

Data sources will exist in a suspended state until someone requests for the data. This makes channels hot.
