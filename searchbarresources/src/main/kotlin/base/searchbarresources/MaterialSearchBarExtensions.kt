package base.searchbarresources

import com.mancj.materialsearchbar.MaterialSearchBar

fun MaterialSearchBar.setup(block: MaterialSearchBarBuilder.() -> Unit): MaterialSearchBar {
    val builder = MaterialSearchBarBuilder(this).apply(block)
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
            builder.onSearchStateChanged?.invoke(enabled)
        }

        override fun onSearchConfirmed(text: CharSequence?) {
            builder.onSearch?.invoke(text)
        }
    })
    return this
}