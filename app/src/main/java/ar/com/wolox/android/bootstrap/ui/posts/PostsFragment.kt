package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.wolox.android.bootstrap.databinding.FragmentPostsBinding
import ar.com.wolox.android.bootstrap.ui.adapter.PostsAdapter
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : BaseFragment<Nothing, PostsViewModel>() {

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
