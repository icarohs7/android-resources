package base.facebookauthresources.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import base.authresources.domain.AuthenticationType
import base.authresources.presentation.authentication.AuthenticationActivity
import base.authresources.presentation.fragments.BaseLoginFragment
import base.corelibrary.domain.extensions.coroutines.launch
import base.facebookauthresources.R
import base.facebookauthresources.databinding.FragmentFacebookLoginBinding
import com.github.icarohs7.unoxandroidarch.toplevel.onActivity
import com.jaychang.sa.AuthCallback
import com.jaychang.sa.SocialUser
import com.jaychang.sa.facebook.SimpleAuth
import kotlinx.coroutines.launch
import splitties.views.onClick

class FacebookLoginFragment : BaseLoginFragment<FragmentFacebookLoginBinding>() {
    private val permissions: List<String> by lazy {
        arguments?.getStringArray(
                PERMISSIONS_ARG)?.toList().orEmpty()
    }

    override fun onBindingCreated(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        super.onBindingCreated(inflater, container, savedInstanceState)
        binding.btnLogin.onClick(::login)
    }

    private fun login() {
        SimpleAuth.connectFacebook(permissions, object : AuthCallback {
            override fun onSuccess(socialUser: SocialUser?) {
                socialUser ?: return

                val type = AuthenticationType.Facebook(
                        id = socialUser.userId,
                        name = socialUser.fullName,
                        email = socialUser.email,
                        pictureUrl = socialUser.profilePictureUrl,
                        token = socialUser.accessToken
                )

                handleLogin(type)
            }

            override fun onCancel() {
                onActivity<AuthenticationActivity> {
                    launch { onLoginError(AuthenticationType.Facebook()) }
                }
            }

            override fun onError(error: Throwable?) {
                onActivity<AuthenticationActivity> {
                    launch { onLoginError(AuthenticationType.Facebook()) }
                }
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_facebook_login
    }

    companion object {
        const val PERMISSIONS_ARG: String = "permissions"

        fun create(permissions: List<String>): FacebookLoginFragment {
            val frag = FacebookLoginFragment()
            frag.arguments = bundleOf(
                    PERMISSIONS_ARG to permissions)
            return frag
        }
    }
}