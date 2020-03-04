package base.corextresources.data.entities

import androidx.core.content.edit
import base.corextresources.domain.extensions.deserialize
import com.chibatching.kotpref.KotprefModel
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.reflect.KProperty

object User : KotprefModel() {
    var id: Int by intPref(0)
    var pin: String by stringPref("")
    var hash: String by stringPref("")
    var name: String by stringPref("")
    var email: String by stringPref("")
    var isRegistered: Boolean by booleanPref(false)

    fun clearUserData() {
        id = 0
        pin = ""
        hash = ""
        name = ""
        email = ""
        isRegistered = false
    }

    private var extraProperties: Map<String, String>
        get() {
            val deserializer = MapSerializer(String.serializer(), String.serializer())
            val serialized = preferences.getString("extraProperties", "{}") ?: "{}"
            return serialized.deserialize(deserializer)
        }
        set(value) {
            val serializer = MapSerializer(String.serializer(), String.serializer())
            preferences.edit {
                putString("extraProperties", Json.stringify(serializer, value))
            }
        }

    operator fun get(key: String): String = extraProperties[key].orEmpty()
    operator fun set(key: String, value: String) {
        extraProperties = extraProperties + Pair(key, value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = this[property.name]
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this[property.name] = value
    }
}