package ar.com.wolox.android.bootstrap.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.wolox.android.bootstrap.network.util.RequestStatus

open class BaseViewModel<V : BaseView> : ViewModel() {

    var view: V? = null
        private set

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus

    fun toggleRequestStatus() {
        _requestStatus.apply {
            value = if (value != RequestStatus.Loading) {
                view?.showLoading()
                RequestStatus.Finished
            } else {
                view?.hideLoading()
                RequestStatus.Loading
            }
        }
    }

    fun onRequestFailed(error: Int) {
        view?.hideLoading()
        _requestStatus.value = RequestStatus.Failure(error)
    }
}
