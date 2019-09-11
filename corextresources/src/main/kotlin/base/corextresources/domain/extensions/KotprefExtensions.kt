package base.corextresources.domain.extensions

import android.content.SharedPreferences
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.execute
import com.chibatching.kotpref.pref.AbstractPref
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate used to store a shared property
 * using json to marshall and unmarshall
 * an object's content
 */
@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> KotprefModel.jsonPref(
        default: T,
        key: String? = null,
        commitByDefault: Boolean = commitAllPropertiesByDefault
): ReadWriteProperty<KotprefModel, T> {
    return object : AbstractPref<T>() {
        override val key: String? = key

        override fun getFromPreference(property: KProperty<*>, preference: SharedPreferences): T {
            return preference.getString(key ?: property.name, null)
                    ?.let { Json.parse<T>(it) }
                    ?: default
        }

        override fun setToPreference(property: KProperty<*>, value: T, preference: SharedPreferences) {
            preference.edit()
                    .putString(key ?: property.name, Json.stringify(value))
                    .execute(commitByDefault)
        }

        override fun setToEditor(property: KProperty<*>, value: T, editor: SharedPreferences.Editor) {
            editor.putString(key ?: property.name, Json.stringify(value))
        }
    }
}