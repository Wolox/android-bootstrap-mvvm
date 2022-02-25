package ar.com.wolox.android.bootstrap.ui.login

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.com.wolox.android.bootstrap.BuildConfig
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentLoginBinding
import ar.com.wolox.android.bootstrap.ui.base.BaseFragment
import ar.com.wolox.android.bootstrap.utils.SnackbarFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun setBinding(inflater: LayoutInflater) = FragmentLoginBinding.inflate(inflater)

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
                else -> showServerError()
            }
        }
    }

    private fun showEmptyUsernameError() {
        binding.usernameInput.error = getString(R.string.empty_username_error)
    }

    private fun showEmptyPasswordError() {
        binding.passwordInput.error = getString(R.string.empty_password_error)
    }

    private fun showInvalidInputError() {
        viewModel.inputErrors.forEach { error ->
            when (error) {
                InputError.EMPTY_USERNAME -> showEmptyUsernameError()
                InputError.EMPTY_PASSWORD -> showEmptyPasswordError()
            }
        }
    }

    private fun showInvalidCredentialsError() {
        SnackbarFactory.create(binding.loginButton, getString(R.string.invalid_credentials), getString(R.string.ok))
    }

    private fun showServerError() {
        SnackbarFactory.create(binding.loginButton, getString(R.string.server_error), getString(R.string.ok))
    }

    private fun goToWoloxSite() = with(Intent(Intent.ACTION_VIEW, BuildConfig.WOLOX_URL.toUri())) {
        startActivity(this)
    }

    private fun goToPosts() {
        findNavController().navigate(R.id.post_fragment)
    }
}
