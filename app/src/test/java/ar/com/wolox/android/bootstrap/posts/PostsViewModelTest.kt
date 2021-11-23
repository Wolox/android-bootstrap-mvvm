package ar.com.wolox.android.bootstrap.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.com.wolox.android.bootstrap.base.BaseViewModelTest
import ar.com.wolox.android.bootstrap.base.MainCoroutineRule
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.ERROR_RESPONSE
import ar.com.wolox.android.bootstrap.posts.PostsTestsConstants.SUCCESSFUL_POSTS_RESPONSE
import ar.com.wolox.android.bootstrap.repository.PostRepository
import ar.com.wolox.android.bootstrap.ui.posts.PostsView
import ar.com.wolox.android.bootstrap.ui.posts.PostsViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@HiltAndroidTest
@ExperimentalCoroutinesApi
class PostsViewModelTest : BaseViewModelTest<PostsView, PostsViewModel>() {

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: PostRepository

    override fun getViewModelInstance(): PostsViewModel = PostsViewModel(repository)

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
    fun `given an unsuccessful response, then no posts are shown`() = runBlocking {
        whenever(repository.getPosts()).thenReturn(Response.error(500, ERROR_RESPONSE.toResponseBody()))
        viewModel.getPosts()
        verify(repository, times(1)).getPosts()
        Assert.assertEquals(viewModel.posts.value!!.size, 0)
    }
}
