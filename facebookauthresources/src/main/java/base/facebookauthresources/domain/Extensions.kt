package base.facebookauthresources.domain

import base.authresources.R
import base.authresources.domain.AuthenticationType
import base.authresources.presentation.authentication.AuthenticationActivity
import base.authresources.presentation.authentication.loadFragment
import base.facebookauthresources.presentation.fragments.FacebookLoginFragment

fun AuthenticationActivity.LoginBuilder.facebook(
        activity: AuthenticationActivity,
        permissions: List<String> = listOf("email"),
        addSwitchButton: Boolean = true,
        handler: Suspend<AuthenticationType.Facebook>
): Unit = with(activity) {
    handlers[AuthenticationType.Facebook::class] = { if (it is AuthenticationType.Facebook) (handler(it)) }
    addButton(R.string.facebook, addSwitchButton) {
        loadFragment(FacebookLoginFragment.create(permissions))
    }
}