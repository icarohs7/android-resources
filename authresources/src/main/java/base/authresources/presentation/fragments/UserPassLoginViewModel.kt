package base.authresources.presentation.fragments

import androidx.lifecycle.MutableLiveData
import base.corextresources.presentation._baseclasses.CoreScopedViewModel

class UserPassLoginViewModel : CoreScopedViewModel() {
    val user: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
}