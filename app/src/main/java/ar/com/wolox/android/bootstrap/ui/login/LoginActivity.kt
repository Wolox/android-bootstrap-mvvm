package ar.com.wolox.android.bootstrap.ui.login

import android.os.Bundle
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportActionBar?.hide()
        replaceFragment(LoginFragment.newInstance())
    }
}
