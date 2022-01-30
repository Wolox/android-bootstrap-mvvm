package ar.com.wolox.android.bootstrap.ui.root

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    val viewModel: RootViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        if (viewModel.isUserLogged) {
            PostsActivity.start(this)
        } else {
            LoginActivity.start(this)
        }
    }
}
