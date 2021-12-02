package ar.com.wolox.android.bootstrap.ui.base

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.com.wolox.android.bootstrap.R

open class BaseActivity : AppCompatActivity() {

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // TODO: Replace with View Binding
    fun showLoading() {
        Log.e("asd", "asd")
        findViewById<ProgressBar>(R.id.loading).visibility = View.VISIBLE
        findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.GONE
    }

    // TODO: Replace with View Binding
    fun hideLoading() {
        Log.e("asd", "asdo")
        findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
        findViewById<FrameLayout>(R.id.fragmentContainer).visibility = View.VISIBLE
    }
}
