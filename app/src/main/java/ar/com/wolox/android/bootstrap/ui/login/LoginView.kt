package ar.com.wolox.android.bootstrap.ui.login

interface LoginView {

    fun showInvalidInputError()
    fun showEmptyUsernameError()
    fun showEmptyPasswordError()
    fun showInvalidCredentialsError()
    fun showServerError()
    fun goToWoloxSite()
    fun goToPosts()
}