package ar.com.wolox.android.bootstrap.root

import android.content.SharedPreferences
import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.base.BaseInstrumentedTest
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.posts.PostInstrumentedTestConstants
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import ar.com.wolox.android.bootstrap.ui.root.RootActivity
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import ar.com.wolox.wolmo.testing.espresso.intents.IntentMatchers.checkIntent
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class RootActivityTest : BaseInstrumentedTest() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private fun getDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setBody(
                    Gson().toJson(
                        listOf(
                            Post(
                                PostInstrumentedTestConstants.GENERIC_ID,
                                PostInstrumentedTestConstants.GENERIC_ID,
                                PostInstrumentedTestConstants.POST_TITLE,
                                PostInstrumentedTestConstants.POST_BODY
                            )
                        )
                    )
                ).setResponseCode(Constants.SUCCESS_STATUS_CODE)
            }
        }
    }

    @Before
    fun setUpTest() {
        MockKAnnotations.init(this)
        service.dispatcher = getDispatcher()
        sharedPreferencesManager = SharedPreferencesManager(sharedPreferences)
    }

    @Test
    fun onLoggedUser_postsAreShown() {
        sharedPreferencesManager.store(Constants.USER_IS_LOGGED_KEY, true)
        checkIntent(PostsActivity::class.java.name) {
            ActivityScenario.launch(RootActivity::class.java)
        }
    }

    @Test
    fun onNotLoggedUser_loginIsShown() {
        sharedPreferencesManager.store(Constants.USER_IS_LOGGED_KEY, false)
        checkIntent(LoginActivity::class.java.name) {
            ActivityScenario.launch(RootActivity::class.java)
        }
    }

    fun tearDownPreferences() {
        sharedPreferencesManager.store(Constants.USER_IS_LOGGED_KEY, false)
    }
}
