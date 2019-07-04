package base.authresources.domain

internal typealias LoginHandler = suspend (AuthenticationType) -> Unit
internal typealias Suspend<T> = suspend (T) -> Unit