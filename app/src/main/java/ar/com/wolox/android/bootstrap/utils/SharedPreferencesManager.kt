package ar.com.wolox.android.bootstrap.utils

import android.content.Context
import ar.com.wolox.android.bootstrap.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// TODO: Move this class to Wolmo.
class SharedPreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.default_shared_preferences_name, context.packageName),
        Context.MODE_PRIVATE
    )

    fun store(key: String, value: String?) = sharedPreferences.edit().putString(key, value).apply()

    fun store(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()

    fun store(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()

    fun store(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    fun store(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

    operator fun get(key: String, defValue: String?): String? = sharedPreferences.getString(key, defValue)

    operator fun get(key: String, defValue: Int): Int = sharedPreferences.getInt(key, defValue)

    operator fun get(key: String, defValue: Float): Float = sharedPreferences.getFloat(key, defValue)

    operator fun get(key: String, defValue: Boolean): Boolean = sharedPreferences.getBoolean(key, defValue)

    operator fun get(key: String, defValue: Long): Long = sharedPreferences.getLong(key, defValue)

    fun clearKey(key: String) = sharedPreferences.edit().remove(key).apply()

    fun keyExists(key: String): Boolean = sharedPreferences.contains(key)
}
