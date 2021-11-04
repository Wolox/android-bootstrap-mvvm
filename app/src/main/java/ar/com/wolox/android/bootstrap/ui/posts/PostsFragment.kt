package ar.com.wolox.android.bootstrap.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.com.wolox.android.bootstrap.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment: Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel: PostsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater)
        viewModel.getPosts()
        return binding.root
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}
