package ar.com.wolox.android.bootstrap.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.wolox.android.bootstrap.network.util.RequestStatus

open class BaseViewModel: ViewModel() {

    protected val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus
}
