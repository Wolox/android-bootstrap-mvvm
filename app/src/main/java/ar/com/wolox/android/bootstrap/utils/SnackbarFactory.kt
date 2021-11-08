package ar.com.wolox.android.bootstrap.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

// TODO: Move this object to Wolmo.
object SnackbarFactory {

    fun create(
        view: View,
        text: String,
        dismissText: String
    ) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
            setAction(dismissText) { dismiss() }
            show()
        }
    }
}
