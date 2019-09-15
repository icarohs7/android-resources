package base.searchbarresources

import com.mancj.materialsearchbar.MaterialSearchBar
import splitties.resources.str

class MaterialSearchBarBuilder(private val searchBar: MaterialSearchBar) {
    var hint: CharSequence = ""
    var hintRes: Int
        @Deprecated(message = "Not implemented") get() = throw NotImplementedError()
        set(value) {
            hint = searchBar.str(value)
        }

    var placeholder: CharSequence = ""
    var placeholderRes: Int
        @Deprecated(message = "Not implemented") get() = throw NotImplementedError()
        set(value) {
            placeholder = searchBar.str(value)
        }

    internal var onButtonClick: ((buttonCode: Int) -> Unit)? = null
    internal var onBackClick: (() -> Unit)? = null
    internal var onHamburguerMenuClick: (() -> Unit)? = null
    internal var onSpeechClick: (() -> Unit)? = null
    internal var onSearchStateChanged: ((isSearchEnabled: Boolean) -> Unit)? = null
    internal var onSearch: ((text: CharSequence?) -> Unit)? = null

    fun onButtonClick(block: (buttonCode: Int) -> Unit) {
        onButtonClick = block
    }

    fun onBackClick(block: () -> Unit) {
        onBackClick = block
    }

    fun onHamburguerMenuClick(block: () -> Unit) {
        onHamburguerMenuClick = block
    }

    fun onSpeechClick(block: () -> Unit) {
        onSpeechClick = block
    }

    fun onSearchStateChanged(block: (isSearchEnabled: Boolean) -> Unit) {
        onSearchStateChanged = block
    }

    fun onSearch(block: (text: CharSequence?) -> Unit) {
        onSearch = block
    }

    fun withSearchBar(block: MaterialSearchBar.() -> Unit) {
        block(searchBar)
    }
}