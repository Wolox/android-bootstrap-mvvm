package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import ar.com.wolox.android.bootstrap.databinding.FragmentPostsBinding
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment: BaseFragment() {

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
        viewModel.getPosts()
        viewModel.posts.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.first().body, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}
