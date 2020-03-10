package base.domaindefinitionresources.data.entities

import base.domaindefinitionresources.presentation.DomainDefinitionActivity
import com.chibatching.kotpref.KotprefModel

object DomainHolder : KotprefModel() {
    var domain: String by stringPref("")
    val domainBase: String
        get() = domain
            .replace("https://", "")
            .replace(DomainDefinitionActivity.domainUrlSuffix, "")
            .replace("/webservices/app/", "")
}