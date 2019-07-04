package base.domaindefinitionresources.data.entities

import com.chibatching.kotpref.KotprefModel

object DomainHolder : KotprefModel() {
    var domain: String by stringPref("")
}