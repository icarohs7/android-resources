package base.domaindefinitionresources.data.entities

import com.chibatching.kotpref.KotprefModel

object DomainHolder : KotprefModel() {
    var domain: String by stringPref("")
    val domainBase: String
        get() = domain
                .replace("https://", "")
                .replace(".sige.pro", "")
                .replace("/webservices/app/", "")
}