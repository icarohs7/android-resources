package base.authresources.presentation.fragments

import androidx.core.view.isGone
import androidx.databinding.ViewDataBinding
import arrow.core.Failure
import arrow.core.Try
import base.authresources.domain.AuthenticationType
import base.authresources.presentation.authentication.AuthenticationActivity
import base.authresources.presentation.authentication.loadFragment
import base.corextresources.data.entities.User
import base.corextresources.data.isLogged
import base.corextresources.domain.extensions.coroutines.launch
import base.corextresources.presentation._baseclasses.BaseBindingFragment
import base.coreresources.extensions.hideKeyboard
import base.coreresources.toplevel.onActivity

abstract class BaseLoginFragment<DB : ViewDataBinding> : BaseBindingFragment<DB>() {
    fun handleLogin(type: AuthenticationType) {
        onActivity<AuthenticationActivity> {
            val handler = handlers[type::class] ?: return@onActivity
            launch {
                with(binding) {
                    stateView.displayLoadingState()
                    stateView.hideKeyboard()

                    val result = Try { handler(type) }
                    if (result is Failure) {
                        stateView.hideStates()
                        onLoginError(type, result.exception)
                        return@with
                    }

                    when (User.isLogged()) {
                        true -> with(binding) {
                            layoutMainContent.removeAllViews()
                            loadFragment(BootingFragment())
                            layoutLogo.isGone = true
                            layoutButtonPanel.isGone = true
                            stateView.hideStates()
                            onLoginSuccess(type)
                        }

                        false -> {
                            stateView.hideStates()
                            onLoginError(type)
                        }
                    }
                }
            }
        }
    }
}