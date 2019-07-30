package base.fluxarchresources.domain

typealias Reducer<T> = T.() -> T
typealias Action<T> = T.() -> Unit