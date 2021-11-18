package ar.com.wolox.android.bootstrap.ui.login

import ar.com.wolox.android.bootstrap.ui.base.BaseView

interface LoginView : BaseView {

    fun showInvalidInputError()
    fun showEmptyUsernameError()
    fun showEmptyPasswordError()
    fun showInvalidCredentialsError()
    fun showServerError()
    fun goToWoloxSite()
    fun goToPosts()
}
