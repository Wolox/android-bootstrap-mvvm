package ar.com.wolox.android.bootstrap.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.com.wolox.android.bootstrap.Constants.INTERNAL_SERVER_ERROR_STATUS_CODE
import ar.com.wolox.android.bootstrap.base.MainCoroutineRule
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.ERROR_RESPONSE
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.posts.PostsTestsConstants.SUCCESSFUL_POSTS_RESPONSE
import ar.com.wolox.android.bootstrap.repository.PostRepository
import ar.com.wolox.android.bootstrap.ui.posts.PostsViewModel
import ar.com.wolox.android.bootstrap.utils.SharedPreferencesManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostsViewModelTest {

    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = getViewModelInstance()
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: PostRepository

    @Mock
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private fun getViewModelInstance(): PostsViewModel = PostsViewModel(repository, sharedPreferencesManager)

    @Test
    fun `given an empty response, then no posts are shown`() = runBlocking {
        whenever(repository.getPosts()).thenReturn(Response.success(listOf()))
        viewModel.getPosts()
        verify(repository, times(1)).getPosts()
        Assert.assertEquals(viewModel.posts.value!!.size, 0)
    }

    @Test
    fun `given a successful response, then the posts are shown`() = runBlocking {
        whenever(repository.getPosts()).thenReturn(Response.success(SUCCESSFUL_POSTS_RESPONSE))
        viewModel.getPosts()
        verify(repository, times(1)).getPosts()
        Assert.assertEquals(viewModel.posts.value!!.size, 1)
    }

    @Test
    fun `given an unsuccessful response, then an error message is shown`() = runBlocking {
        whenever(repository.getPosts()).thenReturn(Response.error(INTERNAL_SERVER_ERROR_STATUS_CODE, ERROR_RESPONSE.toResponseBody()))
        viewModel.getPosts()
        verify(repository, times(1)).getPosts()
        Assert.assertEquals(INTERNAL_SERVER_ERROR_STATUS_CODE, (viewModel.requestStatus.value as RequestStatus.Failure).error)
    }
}
