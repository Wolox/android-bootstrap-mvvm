package ar.com.wolox.android.bootstrap

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ar.com.wolox.android.bootstrap.base.BaseInstrumentedTest
import ar.com.wolox.android.bootstrap.repository.UserRepository
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginViewModel
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class TestEspresso : BaseInstrumentedTest() {

    private lateinit var activity: BaseActivity

    @Mock
    lateinit var repository: UserRepository

    @Mock
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    val viewModel: LoginViewModel
        get() = LoginViewModel(repository, sharedPreferencesManager)

    @Before
    fun aw() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity = it }
    }

    @Test
    fun a() {
        activity.bind
    }
}
