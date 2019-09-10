package base.corextresources.presentation.custom

import base.corextresources.domain.extensions.selectedItemIndex
import base.corextresources.databinding.CustomMaterialDropdownMenuBinding

val CustomMaterialDropdownMenuBinding.selectedItem: String?
    get() = txtDropdown.text?.toString()

var CustomMaterialDropdownMenuBinding.selectedIndex: Int
    get() = txtDropdown.selectedItemIndex
    set(value) {
        txtDropdown.selectedItemIndex = value
    }

var CustomMaterialDropdownMenuBinding.dropdownText: String?
    get() = txtDropdown.text?.toString()
    set(value) = txtDropdown.setText(value)
