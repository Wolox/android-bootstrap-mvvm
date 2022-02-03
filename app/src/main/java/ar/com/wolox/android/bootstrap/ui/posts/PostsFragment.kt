package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.wolox.android.bootstrap.Constants.INTERNAL_SERVER_ERROR_STATUS_CODE
import ar.com.wolox.android.bootstrap.Constants.NOT_FOUND_STATUS_CODE
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentPostsBinding
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.ui.adapter.PostsAdapter
import ar.com.wolox.android.bootstrap.ui.root.RootViewModel
import ar.com.wolox.android.bootstrap.utils.SnackbarFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null

    private val binding get() = _binding!!

    val viewModel: PostsViewModel by viewModels()
    private val rootViewModel: RootViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.isUserLogged) {
            // TODO: Is there a better way to always show the actionbar in a particular fragment?
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
            getPosts()
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.hide()
            findNavController().navigate(R.id.login_fragment)
        }

        setObservers()
    }

    private fun showLoading() {
        rootViewModel.showLoading()
    }

    private fun hideLoading() {
        rootViewModel.hideLoading()
    }

    private fun setObservers() {
        viewModel.requestStatus.observe(viewLifecycleOwner) {
            when (it) {
                RequestStatus.Loading -> showLoading()
                is RequestStatus.Failure -> {
                    hideLoading()
                    binding.postRecyclerView.visibility = View.GONE
                    when (it.error) {
                        // Handle every possible error here
                        NOT_FOUND_STATUS_CODE -> showErrorSnackbar()
                        INTERNAL_SERVER_ERROR_STATUS_CODE -> showErrorSnackbar()
                        else -> showErrorSnackbar()
                    }
                }
                else -> hideLoading()
            }
        }
    }

    private fun showErrorSnackbar() {
        SnackbarFactory.create(
            binding.postRecyclerView,
            getString(R.string.posts_error),
            getString(R.string.ok)
        )
    }

    private fun showEmptyListSnackbar() {
        SnackbarFactory.create(
            binding.postRecyclerView,
            getString(R.string.no_posts_to_show),
            getString(R.string.ok)
        )
    }

    private fun getPosts() {
        viewModel.apply {
            getPosts()
            posts.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.postRecyclerView.apply {
                        adapter = PostsAdapter().apply {
                            submitList(it)
                        }
                        layoutManager = LinearLayoutManager(requireContext())
                        isNestedScrollingEnabled = false
                        isFocusable = false
                        visibility = View.VISIBLE
                    }
                } else {
                    binding.postRecyclerView.visibility = View.GONE
                    showEmptyListSnackbar()
                }
            }
        }
    }
}
