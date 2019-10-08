package base.fluxarchresources

typealias Reducer<T> = T.() -> T
typealias SuspendReducer<T> = suspend T.() -> T
