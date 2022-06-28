package ar.com.wolox.android.bootstrap.ui.root

import androidx.activity.viewModels
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : BaseActivity() {

    private val viewModel: RootViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        if (viewModel.isUserLogged) {
            PostsActivity.start(this)
        } else {
            LoginActivity.start(this)
        }
    }
}
