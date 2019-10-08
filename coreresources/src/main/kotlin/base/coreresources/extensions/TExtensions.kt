package base.coreresources.extensions

@Suppress("UNCHECKED_CAST") fun <T> Any.cast(): T = this as T
val Any.unit: Unit get() = Unit