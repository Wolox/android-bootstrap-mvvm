package ar.com.wolox.android.bootstrap.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import ar.com.wolox.android.bootstrap.databinding.FragmentLoginBinding
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment(), LoginView {

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setListeners() {
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.login(
                    usernameInput.text.toString(),
                    passwordInput.text.toString()
                )
            }
            wolox.setOnClickListener {
                goToWoloxSite()
            }
        }
    }

    override fun setObservers() {
        viewModel.login.observe(viewLifecycleOwner) {
            when (it) {
                LoginResponse.SUCCESS -> goToPosts()
                LoginResponse.INVALID_CREDENTIALS -> showInvalidCredentialsError()
                LoginResponse.INVALID_INPUT -> showInvalidInputError()
                LoginResponse.FAILURE -> showServerError()
            }
        }
    }


    override fun showEmptyUsernameError() {
        binding.usernameInput.error = "Username must not be empty"
    }

    override fun showEmptyPasswordError() {
        binding.passwordInput.error = "Password must not be empty"
    }

    override fun showInvalidInputError() {
        viewModel.inputErrors.forEach {
            it.showError(this)
        }
    }

    override fun showInvalidCredentialsError() {
        val snackbar = Snackbar.make(
            binding.loginButton,
            "Invalid credentials",
            Snackbar.LENGTH_LONG,
        ).run {
            setAction("Aceptar") {
                dismiss()
            }
            show()
        }
    }

    override fun showServerError() {
        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
    }

    override fun goToWoloxSite() = with(Intent(Intent.ACTION_VIEW, WOLOX_URL.toUri())) {
        startActivity(this)
    }

    override fun goToPosts() {
        with(Intent(requireContext(), PostsActivity::class.java)) {
            startActivity(this)
        }
    }

    companion object {
        const val WOLOX_URL = "https://www.wolox.co"
        fun newInstance() = LoginFragment()
    }
}
