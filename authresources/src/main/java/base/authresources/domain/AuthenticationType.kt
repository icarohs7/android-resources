package base.authresources.domain

sealed class AuthenticationType {
    data class Email(val email: String) : AuthenticationType()
    data class Pin(val pin: String) : AuthenticationType()
    data class UserPass(val user: String, val password: String) : AuthenticationType()
    data class Facebook(
        val id: String = "",
        val name: String = "",
        val email: String = "",
        val pictureUrl: String = "",
        val token: String = ""
    ) : AuthenticationType()
}