package ar.com.wolox.android.bootstrap.ui.root

import android.os.Bundle
import androidx.activity.viewModels
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity: BaseActivity() {

    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.userState.observe(this) {
            if (it) {
                PostsActivity.start(this)
            } else {
                LoginActivity.start(this)
            }
        }
        viewModel.isUserLogged()
    }
}
