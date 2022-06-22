package ar.com.wolox.android.bootstrap.posts

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ar.com.wolox.android.bootstrap.Constants
import ar.com.wolox.android.bootstrap.R
import ar.com.wolox.android.bootstrap.base.BaseInstrumentedTest
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.posts.PostInstrumentedTestConstants.GENERIC_ID
import ar.com.wolox.android.bootstrap.posts.PostInstrumentedTestConstants.POST_BODY
import ar.com.wolox.android.bootstrap.posts.PostInstrumentedTestConstants.POST_TITLE
import ar.com.wolox.android.bootstrap.ui.posts.PostsActivity
import ar.com.wolox.wolmo.testing.espresso.text.TextMatchers.checkPopUpText
import ar.com.wolox.wolmo.testing.espresso.visibility.VisibilityMatchers.checkIsGone
import ar.com.wolox.wolmo.testing.espresso.visibility.VisibilityMatchers.checkIsVisible
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class PostsInstrumentedTest : BaseInstrumentedTest() {

    private fun getDispatcher(returnEmptyList: Boolean = false): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (!returnEmptyList) MockResponse().setBody(
                    Gson().toJson(
                        List(3) {
                            Post(
                                GENERIC_ID,
                                GENERIC_ID,
                                POST_TITLE,
                                POST_BODY
                            )
                        }
                    )

                ).setResponseCode(Constants.SUCCESS_STATUS_CODE)
                else MockResponse().setBody(Gson().toJson(emptyList<Post>()))
                    .setResponseCode(Constants.SUCCESS_STATUS_CODE)
            }
        }
    }

    private fun getErrorDispatcher(errorCode: Int): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (errorCode) {
                    404 -> MockResponse().setResponseCode(Constants.NOT_FOUND_STATUS_CODE)
                    500 -> MockResponse().setResponseCode(Constants.INTERNAL_SERVER_ERROR_STATUS_CODE)
                    else -> MockResponse().setResponseCode(Constants.BAD_REQUEST_STATUS_CODE)
                }
            }
        }
    }

    @Test
    fun successfulPostsRequest_shouldShowPostsRecyclerView() {
        service.dispatcher = getDispatcher()
        val view = ActivityScenario.launch(PostsActivity::class.java)
        view?.run {
            checkIsVisible(R.id.postRecyclerView)
            onView(withId(R.id.postRecyclerView)).check(matches(hasChildCount(3)))
        }
    }

    @Test
    fun emptyPostsResponse_shouldHideRecyclerViewAndShowSnackbar() {
        service.dispatcher = getDispatcher(true)
        val view = ActivityScenario.launch(PostsActivity::class.java)
        view?.run {
            checkIsGone(R.id.postRecyclerView)
            checkPopUpText(R.string.no_posts_to_show)
        }
    }

    @Test
    fun notFoundError_shouldHideRecyclerViewAndShowSnackbar() {
        service.dispatcher = getErrorDispatcher(404)
        val view = ActivityScenario.launch(PostsActivity::class.java)
        view?.run {
            checkIsGone(R.id.postRecyclerView)
            checkPopUpText(R.string.posts_error)
        }
    }

    @Test
    fun serverError_shouldHideRecyclerViewAndShowSnackbar() {
        service.dispatcher = getErrorDispatcher(500)
        val view = ActivityScenario.launch(PostsActivity::class.java)
        view?.run {
            checkIsGone(R.id.postRecyclerView)
            checkPopUpText(R.string.posts_error)
        }
    }

    @Test
    fun anyError_shouldHideRecyclerViewAndShowSnackbar() {
        service.dispatcher = getErrorDispatcher(422)
        val view = ActivityScenario.launch(PostsActivity::class.java)
        view?.run {
            checkIsGone(R.id.postRecyclerView)
            checkPopUpText(R.string.posts_error)
        }
    }
}
