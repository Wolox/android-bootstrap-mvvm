package ar.com.wolox.android.bootstrap

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ar.com.wolox.android.bootstrap.base.BaseInstrumentedTest
import ar.com.wolox.android.bootstrap.ui.base.BaseActivity
import ar.com.wolox.android.bootstrap.ui.login.LoginFragment
import ar.com.wolox.android.bootstrap.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class TestEspresso : BaseInstrumentedTest() {

    private lateinit var activity: BaseActivity

//    @get:Rule
//    val rule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun openWoloxUrl() {
        Intents.init()
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            val intent = allOf(hasAction(Intent.ACTION_VIEW), hasData("https://www.wolox.com.ar"))
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
            onView(withId(R.id.passwordInput)).perform(typeText("123456"))
            Espresso.closeSoftKeyboard()
            onView(withId(R.id.loginButton)).perform(click())
            onView(withId(R.id.usernameInput)).check(matches(hasErrorText(getString(R.string.empty_username_error))))
        }
    }

    @Test
    fun emptyPassword_shouldShowErrorMessage() {
        val view = launchFragmentInHiltContainer<LoginFragment>()
        view.run {
            onView(withId(R.id.usernameInput)).perform(typeText("123456"))
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
}
