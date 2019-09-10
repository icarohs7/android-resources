package base.corextresources.domain.extensions

fun <T : Enum<T>> T.toInt(): Int = this.ordinal
inline fun <reified T : Enum<T>> Int.toEnum(): T = enumValues<T>()[this]