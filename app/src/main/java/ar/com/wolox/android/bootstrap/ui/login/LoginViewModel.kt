package ar.com.wolox.android.bootstrap.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.repository.UserRepository
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    var view: LoginView? = null
        private set

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus

    fun toggleRequestStatus() {
        _requestStatus.apply {
            value = if (value != RequestStatus.Loading) {
                view?.showLoading()
                RequestStatus.Loading
            } else {
                view?.hideLoading()
                RequestStatus.Finished
            }
        }
    }

    fun onRequestFailed(error: Int) {
        view?.hideLoading()
        _requestStatus.value = RequestStatus.Failure(error)
    }

    val inputErrors = arrayListOf<InputError>()

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse>
        get() = _login

    fun login(username: String, password: String) {
        inputErrors.clear()
        if (isInputValid(username, password)) {
            toggleRequestStatus()
            viewModelScope.launch {
                val response = userRepository.getUsers()
                if (response.isSuccessful) {
                    val userExists = response.body()!!.any { it.username == username }
                    if (userExists) {
                        _login.value = LoginResponse.SUCCESS
                        sharedPreferencesManager.store(Constants.USER_IS_LOGGED_KEY, true)
                    } else {
                        _login.value = LoginResponse.INVALID_CREDENTIALS
                    }
                } else {
                    _login.value = LoginResponse.FAILURE
                }
                toggleRequestStatus()
            }
        } else {
            _login.value = LoginResponse.INVALID_INPUT
        }
    }

    private fun isInputValid(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            inputErrors.add(InputError.EMPTY_USERNAME)
        }
        if (password.isEmpty()) {
            inputErrors.add(InputError.EMPTY_PASSWORD)
        }
        return inputErrors.isEmpty()
    }
}

enum class LoginResponse {
    SUCCESS,
    INVALID_CREDENTIALS,
    INVALID_INPUT,
    FAILURE
}

enum class InputError {
    EMPTY_USERNAME {
        override fun showError(view: LoginView) {
            view.showEmptyUsernameError()
        }
    },
    EMPTY_PASSWORD {
        override fun showError(view: LoginView) {
            view.showEmptyPasswordError()
        }
    };

    abstract fun showError(view: LoginView)
}
