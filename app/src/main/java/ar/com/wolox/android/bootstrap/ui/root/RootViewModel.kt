package ar.com.wolox.android.bootstrap.ui.root

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RootViewModel : ViewModel() {

    val showLoadingLiveData = MutableLiveData<Unit>()
    val hideLoadingLiveData = MutableLiveData<Unit>()

    fun showLoading() {
        showLoadingLiveData.value = Unit
    }

    fun hideLoading() {
        hideLoadingLiveData.value = Unit
    }
}
