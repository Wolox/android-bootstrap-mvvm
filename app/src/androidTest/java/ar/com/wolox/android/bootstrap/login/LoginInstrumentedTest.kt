package ar.com.wolox.android.bootstrap.login

import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ar.com.wolox.android.bootstrap.BuildConfig
import ar.com.wolox.android.bootstrap.Constants.USERS_PATH
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.base.BaseInstrumentedTest
import ar.com.wolox.android.bootstrap.login.LoginInstrumentedTestConstants.EMPTY_USERS_BODY
import ar.com.wolox.android.bootstrap.login.LoginInstrumentedTestConstants.INVALID_USER_NAME
import ar.com.wolox.android.bootstrap.login.LoginInstrumentedTestConstants.USER_ID
import ar.com.wolox.android.bootstrap.login.LoginInstrumentedTestConstants.VALID_PASSWORD
import ar.com.wolox.android.bootstrap.login.LoginInstrumentedTestConstants.VALID_USER_NAME
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.model.User
import ar.com.wolox.android.bootstrap.ui.login.LoginActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginFragment
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import ar.com.wolox.android.bootstrap.utils.launchFragmentInHiltContainer
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginInstrumentedTest : BaseInstrumentedTest() {

    private fun getDispatcher(isSuccessful: Boolean = false): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.path!!.contains(USERS_PATH)) {
                    return if (isSuccessful) MockResponse().setBody(
                        Gson().toJson(
                            listOf(
                                User(
                                    USER_ID,
                                    VALID_USER_NAME,
                                    VALID_USER_NAME,
                                    VALID_PASSWORD
                                )
                            )
                        )
                    ).setResponseCode(200) else MockResponse().setBody(EMPTY_USERS_BODY)
                        .setResponseCode(200)
                } else MockResponse().setBody(Gson().toJson(emptyArray<Post>()))
            }
        }
    }

    private fun getErrorDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(500)
            }
        }
    }

    @Test
    fun openWoloxUrl() {
        Intents.init()
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            val intent = allOf(hasAction(Intent.ACTION_VIEW), hasData(BuildConfig.WOLOX_URL))
            intending(intent).respondWith(Instrumentation.ActivityResult(0, null))
            onView(withId(R.id.wolox)).perform(click())
            intended(intent)
            Intents.release()
        }
    }

    @Test
    fun basicScreenTest() {
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            onView(withId(R.id.bootstrapIcon)).check(matches(isDisplayed()))
            onView(withId(R.id.usernameInput)).check(matches(isDisplayed()))
            onView(withId(R.id.usernameInput)).check(matches(withHint(R.string.username)))
            onView(withId(R.id.passwordInput)).check(matches(isDisplayed()))
            onView(withId(R.id.passwordInput)).check(matches(withHint(R.string.password)))
            onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
            onView(withId(R.id.loginButton)).check(matches(withText(R.string.login)))
            onView(withId(R.id.wolox)).check(matches(isDisplayed()))
            onView(withId(R.id.wolox)).check(matches(withText(R.string.wolox_company_name)))
        }
    }

    @Test
    fun emptyUsername_shouldShowErrorMessage() {
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            onView(withId(R.id.passwordInput)).perform(typeText(VALID_PASSWORD))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            onView(withId(R.id.usernameInput)).check(matches(hasErrorText(getString(R.string.empty_username_error))))
        }
    }

    @Test
    fun emptyPassword_shouldShowErrorMessage() {
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            onView(withId(R.id.usernameInput)).perform(typeText(VALID_PASSWORD))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            onView(withId(R.id.passwordInput)).check(matches(hasErrorText(getString(R.string.empty_password_error))))
        }
    }

    @Test
    fun emptyData_shouldShowBothErrors() {
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            onView(withId(R.id.loginButton)).perform(click())
            onView(withId(R.id.usernameInput)).check(matches(hasErrorText(getString(R.string.empty_username_error))))
            onView(withId(R.id.passwordInput)).check(matches(hasErrorText(getString(R.string.empty_password_error))))
        }
    }

    @Test
    fun invalidCredentials_shouldShowSnackbarError() {
        service.dispatcher = getDispatcher()
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            onView(withId(R.id.usernameInput)).perform(typeText(INVALID_USER_NAME))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.passwordInput)).perform(typeText(VALID_PASSWORD))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            onView(withText(R.string.invalid_credentials)).check(
                matches(
                    withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                    )
                )
            )
        }
    }

    @Test
    fun validCredentials_goToPosts() {
        Intents.init()
        service.dispatcher = getDispatcher(true)
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            onView(withId(R.id.usernameInput)).perform(typeText(VALID_USER_NAME))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.passwordInput)).perform(typeText(VALID_PASSWORD))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            intended(hasComponent(PostsActivity::class.java.name))
            Intents.release()
        }
    }

    @Test
    fun serverError_shouldShowSnackbarError() {
        service.dispatcher = getErrorDispatcher()
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            onView(withId(R.id.usernameInput)).perform(typeText(VALID_USER_NAME))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.passwordInput)).perform(typeText(VALID_PASSWORD))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            onView(withText(R.string.server_error)).check(
                matches(
                    withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                    )
                )
            )
        }
    }
}
