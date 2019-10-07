package base.retrofitresources.domain.toplevel

import kotlinx.serialization.Serializable

@Serializable
data class TestClass(val id: Int = 0, val message: String)