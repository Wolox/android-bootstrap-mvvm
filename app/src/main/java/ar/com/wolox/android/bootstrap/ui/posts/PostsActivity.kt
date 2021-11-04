package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, PostsFragment.newInstance())
            .commit()
    }
}
