package ar.com.wolox.android.bootstrap.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentMainBinding
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            goToPosts.setOnClickListener {
                with(Intent(requireContext(), PostsActivity::class.java)) {
                    requireContext().startActivity(this)
                }
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
