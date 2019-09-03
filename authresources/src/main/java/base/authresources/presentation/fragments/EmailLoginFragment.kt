package base.authresources.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import base.authresources.R
import base.authresources.databinding.FragmentEmailLoginBinding
import base.authresources.domain.AuthenticationType

class EmailLoginFragment : BaseLoginFragment<FragmentEmailLoginBinding>() {
    private val viewmodel: EmailLoginViewModel by viewModels()

    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.email = viewmodel.email
        binding.setLoginHandler {
            val email = viewmodel.email.value ?: return@setLoginHandler
            handleLogin(AuthenticationType.Email(email))
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_email_login
    }
}