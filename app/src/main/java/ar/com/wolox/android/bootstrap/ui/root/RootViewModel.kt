package ar.com.wolox.android.bootstrap.ui.root

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.ui.base.BaseViewModel
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
): BaseViewModel<Nothing>() {

    private val _userState = MutableLiveData<Boolean>()
    val userState: LiveData<Boolean>
        get() = _userState

    fun isUserLogged() {
        _userState.value = sharedPreferencesManager[Constants.USER_IS_LOGGED_KEY, false]
    }
}
