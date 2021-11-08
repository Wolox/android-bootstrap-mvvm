package ar.com.wolox.android.bootstrap.ui.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        replaceFragment(PostsFragment.newInstance())
    }

    companion object {
        fun start(context: Context) {
            with(Intent(context, PostsActivity::class.java)) {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(this)
            }
        }
    }
}
