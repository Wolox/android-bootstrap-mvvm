package ar.com.wolox.android.bootstrap.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.com.wolox.android.bootstrap.BuildConfig
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.databinding.FragmentLoginBinding
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.ui.root.RootViewModel
import ar.com.wolox.android.bootstrap.utils.SnackbarFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    val viewModel: LoginViewModel by viewModels()
    private val rootViewModel: RootViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun showLoading() {
        rootViewModel.showLoading()
    }

    private fun hideLoading() {
        rootViewModel.hideLoading()
    }

    private fun setListeners() {
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

    private fun setObservers() {
        viewModel.requestStatus.observe(viewLifecycleOwner) {
            when (it) {
                RequestStatus.Loading -> showLoading()
                else -> hideLoading()
            }
        }

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
