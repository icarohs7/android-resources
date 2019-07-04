package base.authresources.presentation.fragments

import androidx.core.view.isGone
import androidx.databinding.ViewDataBinding
import base.authresources.domain.AuthenticationType
import base.authresources.presentation.authentication.AuthenticationActivity
import base.authresources.presentation.authentication.loadFragment
import base.corelibrary.data.entities.User
import base.corelibrary.data.isLogged
import com.github.icarohs7.unoxandroidarch.extensions.hideKeyboard
import com.github.icarohs7.unoxandroidarch.presentation.fragments.BaseBindingFragment
import com.github.icarohs7.unoxandroidarch.toplevel.onActivity
import kotlinx.coroutines.launch

abstract class BaseLoginFragment<DB : ViewDataBinding> : BaseBindingFragment<DB>() {
    fun handleLogin(type: AuthenticationType) {
        onActivity<AuthenticationActivity> {
            val handler = handlers[type::class] ?: return@onActivity
            launch {
                with(binding) {
                    stateView.displayLoadingState()
                    stateView.hideKeyboard()

                    handler(type)

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