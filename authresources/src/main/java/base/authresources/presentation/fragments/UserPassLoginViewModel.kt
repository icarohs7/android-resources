package base.authresources.presentation.fragments

import androidx.lifecycle.MutableLiveData
import com.github.icarohs7.unoxandroidarch.presentation.viewmodel.BaseScopedViewModel

class UserPassLoginViewModel : BaseScopedViewModel() {
    val user: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
}