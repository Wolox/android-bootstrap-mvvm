package ar.com.wolox.android.bootstrap.ui.root

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ar.com.wolox.android.bootstrap.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.showLoadingLiveData.observe(
            this,
            Observer {
                showLoading()
            }
        )

        viewModel.hideLoadingLiveData.observe(
            this,
            Observer {
                hideLoading()
            }
        )
    }

    private fun showLoading() {
        binding.apply {
            loading.visibility = View.VISIBLE
            fragmentContainer.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        binding.apply {
            loading.visibility = View.GONE
            fragmentContainer.visibility = View.VISIBLE
        }
    }
}
