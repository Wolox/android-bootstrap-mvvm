package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
class PostsFragment : BaseFragment<FragmentPostsBinding, PostsViewModel>() {

    override val viewModel: PostsViewModel by viewModels()

    override fun setBinding(inflater: LayoutInflater) = FragmentPostsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isUserLogged) {
            // TODO: Is there a better way to always show the actionbar in a particular fragment?
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
            viewModel.getPosts()
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.hide()
            findNavController().navigate(R.id.login_fragment)
        }
    }

    override fun setObservers() {
        viewModel.apply {
            requestStatus.observe(viewLifecycleOwner) {
                if (it is RequestStatus.Failure) {
                    binding.postRecyclerView.visibility = View.GONE
                    when (it.error) {
                        // Handle every possible error here
                        NOT_FOUND_STATUS_CODE -> showErrorSnackbar()
                        INTERNAL_SERVER_ERROR_STATUS_CODE -> showErrorSnackbar()
                        else -> showErrorSnackbar()
                    }
                }
            }

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
}
