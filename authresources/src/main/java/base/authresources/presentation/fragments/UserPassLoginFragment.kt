package base.authresources.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import base.authresources.R
import base.authresources.databinding.FragmentUserPassLoginBinding
import base.authresources.domain.AuthenticationType

class UserPassLoginFragment : BaseLoginFragment<FragmentUserPassLoginBinding>() {
    private val viewmodel: UserPassLoginViewModel by viewModels()

    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.user = viewmodel.user
        binding.password = viewmodel.password
        binding.setLoginHandler {
            val user = viewmodel.user.value ?: return@setLoginHandler
            val password = viewmodel.password.value ?: return@setLoginHandler
            handleLogin(AuthenticationType.UserPass(user, password))
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_user_pass_login
    }
}