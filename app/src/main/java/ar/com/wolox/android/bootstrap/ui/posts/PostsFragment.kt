package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.wolox.android.bootstrap.Constants.INTERNAL_SERVER_ERROR_STATUS_CODE
import ar.com.wolox.android.bootstrap.Constants.NOT_FOUND_STATUS_CODE
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentPostsBinding
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.ui.adapter.PostsAdapter
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import ar.com.wolox.android.bootstrap.utils.SnackbarFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : BaseFragment<PostsView, PostsViewModel>(), PostsView {

    private lateinit var binding: FragmentPostsBinding

    override val viewModel: PostsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater)
        getPosts()
        return binding.root
    }

    override fun setObservers() {
        viewModel.requestStatus.observe(viewLifecycleOwner) {
            if (it is RequestStatus.Failure) {
                when (it.error) {
                    // Handle every possible error here
                    INTERNAL_SERVER_ERROR_STATUS_CODE -> showErrorSnackbar()
                }
            }
        }
    }

    override fun showErrorSnackbar() {
        SnackbarFactory.create(binding.postRecyclerView, getString(R.string.posts_error), getString(R.string.ok))
    }

    private fun getPosts() {
        viewModel.apply {
            getPosts()
            posts.observe(viewLifecycleOwner) {
                binding.postRecyclerView.apply {
                    adapter = PostsAdapter().apply {
                        submitList(it)
                    }
                    layoutManager = LinearLayoutManager(requireContext())
                    isNestedScrollingEnabled = false
                    isFocusable = false
                }
            }
        }
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}
