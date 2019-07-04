package base.domaindefinitionresources.presentation

import android.os.Bundle
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import base.corelibrary.databinding.CustomInputFieldBinding
import base.corelibrary.presentation.CoreNavigation
import base.domaindefinitionresources.R
import base.domaindefinitionresources.data.entities.DomainHolder
import base.domaindefinitionresources.databinding.ActivityDomainDefinitionBinding
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import com.github.icarohs7.unoxandroidarch.toplevel.showErrorFlashBar
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import kotlinx.coroutines.launch

open class DomainDefinitionActivity : BaseBindingActivity<ActivityDomainDefinitionBinding>() {
    private val layoutEditDomainBinding by lazy {
        binding.layoutEditDomain as CustomInputFieldBinding
    }

    open fun onInvalidDomain() {
        showErrorFlashBar("Domínio inválido")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onDomainDefinedBeforeCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        setupBinding()
    }

    private fun setupBinding(): Unit = with(binding) {
        btnConfirm.setOnClickListener { setDomain() }
        layoutEditDomainBinding.editText.inputType = InputType.TYPE_TEXT_VARIATION_URI
    }

    private fun setDomain() {
        when (val domain = getDomain()) {
            None -> onInvalidDomain()
            is Some -> launch {
                onBackground { DomainHolder.domain = domain.t }
                onDomainDefined()
            }
        }
    }

    private fun getDomain(): Option<String> {
        val value = "${layoutEditDomainBinding.editText.text ?: ""}"
        return when (isValid(value)) {
            true -> Option.just("https://$value.sige.pro/webservices/app/")
            false -> None
        }
    }

    private fun isValid(string: String): Boolean {
        return string.isNotBlank()
    }

    open fun onDomainDefinedBeforeCreate() {
        launch {
            domainDefinitionListener(onBackground { DomainHolder.domain })
            when {
                DomainHolder.domain.isBlank() -> Unit
                else -> CoreNavigation.splashActivity()
            }
        }
    }

    open fun onDomainDefined() {
        launch {
            domainDefinitionListener(onBackground { DomainHolder.domain })
            CoreNavigation.splashActivity()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_domain_definition
    }

    companion object {
        private var domainDefinitionListener: DomainDefinitionActivity.(String) -> Unit = {}
        fun onDomainDefined(listener: DomainDefinitionActivity.(String) -> Unit) {
            domainDefinitionListener = listener
        }
    }
}