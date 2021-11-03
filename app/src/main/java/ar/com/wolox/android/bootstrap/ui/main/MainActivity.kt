package ar.com.wolox.android.bootstrap.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.com.wolox.android.bootstrap.R

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MainFragment.newInstance())
            .commit()
    }
}
