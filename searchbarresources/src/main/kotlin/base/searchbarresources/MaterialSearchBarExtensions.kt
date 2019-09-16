package base.searchbarresources

import android.util.Log
import android.widget.ImageView
import base.coreresources.extensions.hideKeyboard
import com.mancj.materialsearchbar.MaterialSearchBar

fun MaterialSearchBar.setup(block: MaterialSearchBarBuilder.() -> Unit) {
    val builder = MaterialSearchBarBuilder(this).apply(block)
    builder.onBackClick = builder.onBackClick ?: { onSearch(builder, null) }
    
    setHint(builder.hint)
    setPlaceHolder(builder.placeholder)
    setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
        override fun onButtonClicked(buttonCode: Int) {
            builder.onButtonClick?.invoke(buttonCode)
            when (buttonCode) {
                MaterialSearchBar.BUTTON_BACK -> builder.onBackClick?.invoke()
                MaterialSearchBar.BUTTON_NAVIGATION -> builder.onHamburguerMenuClick?.invoke()
                MaterialSearchBar.BUTTON_SPEECH -> builder.onSpeechClick?.invoke()
            }
        }

        override fun onSearchStateChanged(enabled: Boolean) {
            if (!enabled) {
                val search = this@setup.text
                if (search.isBlank()) setPlaceHolder(builder.placeholder)
                else setPlaceHolder(text)
            }
            builder.onSearchStateChanged?.invoke(enabled)
        }

        override fun onSearchConfirmed(text: CharSequence?) {
            onSearch(builder, text)
        }
    })
    clearIcon.setOnClickListener {
        onClick(it)
        onSearch(builder, null)
    }
}

/**
 * Search for the given text, hiding the keyboard, removing the
 * focus of the component and changing the placeholder to the
 * search term or the original placeholder if it's empty
 */
private fun MaterialSearchBar.onSearch(builder: MaterialSearchBarBuilder, text: CharSequence?) {
    hideKeyboard()
    clearFocus()
    builder.onSearch?.invoke(text)
    disableSearch()
}

/**
 * Use reflection to obtain access to the
 * clear icon view on the search bar
 */
private val MaterialSearchBar.clearIcon: ImageView
    get() {
        return this::class
                .java
                .getDeclaredField("clearIcon")
                .apply { isAccessible = true }
                .get(this)
                as ImageView
    }