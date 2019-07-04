package base.authresources.presentation.fragments

import androidx.lifecycle.MutableLiveData
import com.github.icarohs7.unoxandroidarch.presentation.viewmodel.BaseScopedViewModel

class EmailLoginViewModel : BaseScopedViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
}