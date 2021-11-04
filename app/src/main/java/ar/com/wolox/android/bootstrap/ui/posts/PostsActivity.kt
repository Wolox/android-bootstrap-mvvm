package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.com.wolox.android.bootstrap.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, PostsFragment.newInstance())
            .commit()
    }
}
