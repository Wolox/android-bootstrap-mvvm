package ar.com.wolox.android.bootstrap.login

import androidx.test.core.app.ActivityScenario
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
import ar.com.wolox.wolmo.testing.espresso.actions.ActionsHelper.singleClick
import ar.com.wolox.wolmo.testing.espresso.intents.IntentMatchers.checkIntent
import ar.com.wolox.wolmo.testing.espresso.intents.IntentMatchers.checkIntentWithActionView
import ar.com.wolox.wolmo.testing.espresso.text.TextHelpers.writeText
import ar.com.wolox.wolmo.testing.espresso.text.TextMatchers.checkErrorText
import ar.com.wolox.wolmo.testing.espresso.text.TextMatchers.checkHintMatches
import ar.com.wolox.wolmo.testing.espresso.text.TextMatchers.checkPopUpText
import ar.com.wolox.wolmo.testing.espresso.text.TextMatchers.checkTextMatches
import ar.com.wolox.wolmo.testing.espresso.visibility.VisibilityMatchers.checkIsVisible
import ar.com.wolox.wolmo.testing.hilt.launchHiltFragment
import ar.com.wolox.wolmo.testing.model.Assertion
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
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
        val view = launchHiltFragment<LoginFragment>()
        view.run {
            checkIntentWithActionView(BuildConfig.WOLOX_URL) {
                singleClick(R.id.wolox)
            }
        }
    }

    @Test
    fun basicScreenTest() {
        val view = launchHiltFragment<LoginFragment>()
        view?.run {
            checkIsVisible(
                R.id.bootstrapIcon,
                R.id.usernameInput,
                R.id.passwordInput,
                R.id.loginButton,
                R.id.wolox
            )
            checkTextMatches(
                Assertion(R.id.loginButton, getString(R.string.login)),
                Assertion(R.id.wolox, getString(R.string.wolox_company_name))
            )
            checkHintMatches(
                Assertion(R.id.usernameInput, getString(R.string.username)),
                Assertion(R.id.passwordInput, getString(R.string.password))
            )
        }
    }

    @Test
    fun emptyUsername_shouldShowErrorMessage() {
        val view = launchHiltFragment<LoginFragment>()
        view?.run {
            writeText(R.id.passwordInput, VALID_PASSWORD)
            singleClick(R.id.loginButton)
            checkErrorText(
                Assertion(R.id.usernameInput, getString(R.string.empty_username_error))
            )
        }
    }

    @Test
    fun emptyPassword_shouldShowErrorMessage() {
        val view = launchHiltFragment<LoginFragment>()
        view?.run {
            writeText(R.id.usernameInput, VALID_USER_NAME)
            singleClick(R.id.loginButton)
            checkErrorText(
                Assertion(
                    R.id.passwordInput,
                    getString(R.string.empty_password_error)
                )
            )
        }
    }

    @Test
    fun emptyData_shouldShowBothErrors() {
        val view = launchHiltFragment<LoginFragment>()
        view?.run {
            singleClick(R.id.loginButton)
            checkErrorText(
                Assertion(
                    R.id.usernameInput, getString(R.string.empty_username_error)
                ),
                Assertion(
                    R.id.passwordInput, getString(R.string.empty_password_error)
                )
            )
        }
    }

    @Test
    fun invalidCredentials_shouldShowSnackbarError() {
        service.dispatcher = getDispatcher()
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            writeText(R.id.usernameInput, INVALID_USER_NAME)
            writeText(R.id.passwordInput, VALID_PASSWORD)
            singleClick(R.id.loginButton)
            checkPopUpText(R.string.invalid_credentials)
        }
    }

    @Test
    fun validCredentials_goToPosts() {
        service.dispatcher = getDispatcher(true)
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            checkIntent(PostsActivity::class.java.name) {
                writeText(R.id.usernameInput, VALID_USER_NAME)
                writeText(R.id.passwordInput, VALID_PASSWORD)
                singleClick(R.id.loginButton)
            }
        }
    }

    @Test
    fun serverError_shouldShowSnackbarError() {
        service.dispatcher = getErrorDispatcher()
        val view = ActivityScenario.launch(LoginActivity::class.java)
        view.run {
            writeText(R.id.usernameInput, VALID_USER_NAME)
            writeText(R.id.passwordInput, VALID_PASSWORD)
            singleClick(R.id.loginButton)
            checkPopUpText(R.string.server_error)
        }
    }
}
