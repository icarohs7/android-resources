package base.authresources.presentation.fragments

import androidx.lifecycle.MutableLiveData
import base.corelibrary.presentation._baseclasses.CoreScopedViewModel

class EmailLoginViewModel : CoreScopedViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
}