package ar.com.wolox.android.bootstrap.ui.root

import androidx.lifecycle.ViewModel
import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    val isUserLogged: Boolean
        get() = sharedPreferencesManager[Constants.USER_IS_LOGGED_KEY, false]
}
