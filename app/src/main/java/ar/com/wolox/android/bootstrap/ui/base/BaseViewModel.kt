package ar.com.wolox.android.bootstrap.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.wolox.android.bootstrap.network.util.RequestStatus

open class BaseViewModel : ViewModel() {

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus

    fun toggleRequestStatus() {
        _requestStatus.apply {
            value = if (value != RequestStatus.Loading) {
                RequestStatus.Loading
            } else {
                RequestStatus.Finished
            }
        }
    }

    fun onRequestFailed(error: Int) {
        _requestStatus.value = RequestStatus.Failure(error)
    }
}
