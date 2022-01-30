package ar.com.wolox.android.bootstrap.ui.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(PostsFragment.newInstance())
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.fragmentContainer.visibility = View.GONE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
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
