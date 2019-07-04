package base.facebookauthresources.domain

import base.authresources.domain.AuthenticationType

internal typealias LoginHandler = suspend (AuthenticationType) -> Unit
internal typealias Suspend<T> = suspend (T) -> Unit