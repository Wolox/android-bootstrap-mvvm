package ar.com.wolox.android.bootstrap.ui.posts

interface PostsView {
    fun showErrorSnackbar()
    fun showEmptyListSnackbar()
    fun showLoading()
    fun hideLoading()
}
