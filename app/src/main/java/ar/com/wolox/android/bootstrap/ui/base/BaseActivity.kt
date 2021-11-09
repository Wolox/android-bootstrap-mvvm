package ar.com.wolox.android.bootstrap.ui.base

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.com.wolox.android.bootstrap.R

open class BaseActivity: AppCompatActivity() {

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // TODO: Replace with View Binding
    fun showLoading() {
        findViewById<ProgressBar>(R.id.loading).visibility = View.VISIBLE
    }

    // TODO: Replace with View Binding
    fun hideLoading() {
        findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
    }
}
