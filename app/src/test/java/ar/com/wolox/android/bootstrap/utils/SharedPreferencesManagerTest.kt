package ar.com.wolox.android.bootstrap.utils

import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyFloat
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

class SharedPreferencesManagerTest {

    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Before
    fun setUp() {
        editor = Mockito.mock(SharedPreferences.Editor::class.java)
        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        setUpPreconditions()
        sharedPreferencesManager = SharedPreferencesManager(sharedPreferences)
    }

    private fun setUpPreconditions() {
        whenever(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor)
        whenever(editor.putFloat(anyString(), anyFloat())).thenReturn(editor)
        whenever(editor.putInt(anyString(), anyInt())).thenReturn(editor)
        whenever(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        whenever(editor.putString(anyString(), anyString())).thenReturn(editor)
        whenever(editor.remove(anyString())).thenReturn(editor)
        whenever(sharedPreferences.edit()).thenReturn(editor)
    }

    @Test
    fun getShouldCallSharedPreferences() {
        with(UtilsTestsConstants) {
            whenever(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenReturn(
                MOCK_BOOLEAN
            )
            whenever(sharedPreferences.getFloat(anyString(), anyFloat())).thenReturn(MOCK_FLOAT)
            whenever(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(MOCK_INT)
            whenever(sharedPreferences.getLong(anyString(), anyLong())).thenReturn(MOCK_LONG)
            whenever(sharedPreferences.getString(anyString(), anyString())).thenReturn(MOCK_STRING)

            Assert.assertEquals(MOCK_BOOLEAN, sharedPreferencesManager[KEY_BOOLEAN, false])
            Assert.assertEquals(MOCK_FLOAT, sharedPreferencesManager[KEY_FLOAT, 0f])
            Assert.assertEquals(MOCK_INT, sharedPreferencesManager[KEY_INT, 0])
            Assert.assertEquals(MOCK_LONG, sharedPreferencesManager[KEY_LONG, 0L])
            Assert.assertEquals(MOCK_STRING, sharedPreferencesManager[KEY_STRING, ""])
        }
    }

    @Test
    fun storeShouldCallEditor() {
        with(UtilsTestsConstants) {
            sharedPreferencesManager.store(KEY_LONG, MOCK_LONG)
            sharedPreferencesManager.store(KEY_FLOAT, MOCK_FLOAT)
            sharedPreferencesManager.store(KEY_STRING, MOCK_STRING)
            sharedPreferencesManager.store(KEY_BOOLEAN, MOCK_BOOLEAN)
            sharedPreferencesManager.store(KEY_INT, MOCK_INT)
            verify(editor, times(5)).apply()
            verify(editor, times(1)).putLong(eq(KEY_LONG), eq(MOCK_LONG))
            verify(editor, times(1)).putFloat(eq(KEY_FLOAT), eq(MOCK_FLOAT))
            verify(editor, times(1)).putString(eq(KEY_STRING), eq(MOCK_STRING))
            verify(editor, times(1)).putBoolean(eq(KEY_BOOLEAN), eq(MOCK_BOOLEAN))
            verify(editor, times(1)).putInt(eq(KEY_INT), eq(MOCK_INT))
        }
    }

    @Test
    fun clearKeyShouldCallEditor() {
        with(UtilsTestsConstants) {
            sharedPreferencesManager.clearKey(KEY_TO_CLEAN)
            verify(editor, times(1)).remove(eq(KEY_TO_CLEAN))
            verify(editor, times(1)).apply()
        }
    }

    @Test
    fun keyExistsShouldCallSharedPreferences() {
        with(UtilsTestsConstants) {
            whenever(sharedPreferences.contains(eq(EXISTENT_KEY))).thenReturn(true)
            Assert.assertTrue(sharedPreferencesManager.keyExists(EXISTENT_KEY))
            Assert.assertFalse(sharedPreferencesManager.keyExists(NON_EXISTENT_KEY))
            verify(sharedPreferences, times(2)).contains(anyString())
        }
    }
}
