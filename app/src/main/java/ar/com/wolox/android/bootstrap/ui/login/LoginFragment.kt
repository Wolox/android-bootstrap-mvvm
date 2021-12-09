package ar.com.wolox.android.bootstrap.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import ar.com.wolox.android.bootstrap.BuildConfig
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentLoginBinding
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import ar.com.wolox.android.bootstrap.utils.SnackbarFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginView, LoginViewModel>(), LoginView {

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
                LoginResponse.SUCCESS -> {
                    goToPosts()
                    requireActivity().finish()
                }
                LoginResponse.INVALID_CREDENTIALS -> showInvalidCredentialsError()
                LoginResponse.INVALID_INPUT -> showInvalidInputError()
                else -> showServerError()
            }
        }
    }

    override fun showEmptyUsernameError() {
        binding.usernameInput.error = getString(R.string.empty_username_error)
    }

    override fun showEmptyPasswordError() {
        binding.passwordInput.error = getString(R.string.empty_password_error)
    }

    override fun showInvalidInputError() {
        viewModel.inputErrors.forEach {
            it.showError(this)
        }
    }

    override fun showInvalidCredentialsError() {
        SnackbarFactory.create(binding.loginButton, getString(R.string.invalid_credentials), getString(R.string.ok))
    }

    override fun showServerError() {
        SnackbarFactory.create(binding.loginButton, getString(R.string.server_error), getString(R.string.ok))
    }

    override fun goToWoloxSite() = with(Intent(Intent.ACTION_VIEW, BuildConfig.WOLOX_URL.toUri())) {
        startActivity(this)
    }

    override fun goToPosts() {
        with(Intent(requireContext(), PostsActivity::class.java)) {
            startActivity(this)
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}
