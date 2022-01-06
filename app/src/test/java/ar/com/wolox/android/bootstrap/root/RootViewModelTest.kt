package ar.com.wolox.android.bootstrap.root

import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.base.BaseViewModelTest
import ar.com.wolox.android.bootstrap.ui.base.BaseView
import ar.com.wolox.android.bootstrap.ui.root.RootViewModel
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RootViewModelTest : BaseViewModelTest<BaseView, RootViewModel>() {

    @Mock
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun getViewModelInstance() = RootViewModel(sharedPreferencesManager)

    @Test
    fun a() {
        whenever(sharedPreferencesManager[Constants.USER_IS_LOGGED_KEY, false]).thenReturn(false)
        val s = viewModel.isUserLogged
        Assert.assertFalse(s)
        verify(sharedPreferencesManager, times(1))[Constants.USER_IS_LOGGED_KEY, false]
    }

    @Test
    fun b() {
        whenever(sharedPreferencesManager[Constants.USER_IS_LOGGED_KEY, false]).thenReturn(true)
        val s = viewModel.isUserLogged
        Assert.assertTrue(s)
        verify(sharedPreferencesManager, times(1))[Constants.USER_IS_LOGGED_KEY, false]
    }
}
