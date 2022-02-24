package ar.com.wolox.android.bootstrap.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.ui.root.RootViewModel

abstract class BaseFragment<M : BaseViewModel> : Fragment() {

    abstract val viewModel: M

    private val rootViewModel: RootViewModel by activityViewModels()

    open fun setListeners() { }

    open fun setObservers() { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.requestStatus.observe(viewLifecycleOwner) {
            when (it) {
                RequestStatus.Loading -> showLoading()
                else -> hideLoading()
            }
        }
        setListeners()
        setObservers()
    }

    private fun showLoading() {
        rootViewModel.showLoading()
    }

    private fun hideLoading() {
        rootViewModel.hideLoading()
    }
}
