package ar.com.wolox.android.bootstrap.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.ui.root.RootViewModel

abstract class BaseFragment<V : ViewDataBinding, M : BaseViewModel> : Fragment() {

    abstract val viewModel: M

    private val rootViewModel: RootViewModel by activityViewModels()

    private var _binding: V? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = this.setBinding(inflater)
        return binding.root
    }

    abstract fun setBinding(inflater: LayoutInflater): V

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
