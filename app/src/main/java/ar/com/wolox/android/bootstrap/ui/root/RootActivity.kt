package ar.com.wolox.android.bootstrap.ui.root

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.ActivityBaseBinding
import ar.com.wolox.android.bootstrap.ui.login.LoginFragment
import ar.com.wolox.android.bootstrap.ui.posts.PostsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isUserLogged) {
            replaceFragment(PostsFragment.newInstance())
        } else {
            supportActionBar?.hide()
            replaceFragment(LoginFragment.newInstance())
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.fragmentContainer.visibility = View.GONE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
