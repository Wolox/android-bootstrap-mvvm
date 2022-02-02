package ar.com.wolox.android.bootstrap.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.com.wolox.android.bootstrap.Constants.INTERNAL_SERVER_ERROR_STATUS_CODE
import ar.com.wolox.android.bootstrap.Constants.USER_IS_LOGGED_KEY
import ar.com.wolox.android.bootstrap.base.MainCoroutineRule
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.EMPTY_PASSWORD
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.EMPTY_USERNAME
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.ERROR_RESPONSE
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.INVALID_USERNAME
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.SUCCESSFUL_USER_LIST
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.VALID_PASSWORD
import ar.com.wolox.android.bootstrap.login.LoginTestConstants.VALID_USERNAME
import ar.com.wolox.android.bootstrap.repository.UserRepository
import ar.com.wolox.android.bootstrap.ui.login.InputError
import ar.com.wolox.android.bootstrap.ui.login.LoginResponse
import ar.com.wolox.android.bootstrap.ui.login.LoginViewModel
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
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

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
    lateinit var repository: UserRepository

    @Mock
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private fun getViewModelInstance() = LoginViewModel(repository, sharedPreferencesManager)

    @Test
    fun `given valid credentials, when the user presses the login button, then the user is logged`() = runBlocking {
        Mockito.`when`(repository.getUsers()).thenReturn(Response.success(SUCCESSFUL_USER_LIST))
        viewModel.login(VALID_USERNAME, VALID_PASSWORD)
        verify(repository, times(1)).getUsers()
        Assert.assertTrue(viewModel.inputErrors.isEmpty())
        Assert.assertEquals(LoginResponse.SUCCESS, viewModel.login.value)
        verify(sharedPreferencesManager, times(1)).store(USER_IS_LOGGED_KEY, true)
    }

    @Test
    fun `given an invalid username, when the user presses the login button, then an invalid credentials message is displayed`() = runBlocking {
        whenever(repository.getUsers()).thenReturn(Response.success(arrayListOf()))
        viewModel.login(INVALID_USERNAME, VALID_PASSWORD)
        verify(repository, times(1)).getUsers()
        Assert.assertTrue(viewModel.inputErrors.isEmpty())
        Assert.assertEquals(LoginResponse.INVALID_CREDENTIALS, viewModel.login.value)
        Assert.assertFalse(sharedPreferencesManager[USER_IS_LOGGED_KEY, false])
    }

    @Test
    fun `given an error in the login service, when the user presses the login button, then an error message is displayed`() = runBlocking {
        whenever(repository.getUsers()).thenReturn(Response.error(INTERNAL_SERVER_ERROR_STATUS_CODE, ERROR_RESPONSE.toResponseBody()))
        viewModel.login(VALID_USERNAME, VALID_PASSWORD)
        verify(repository, times(1)).getUsers()
        Assert.assertTrue(viewModel.inputErrors.isEmpty())
        Assert.assertEquals(LoginResponse.FAILURE, viewModel.login.value)
        Assert.assertFalse(sharedPreferencesManager[USER_IS_LOGGED_KEY, false])
    }

    @Test
    fun `given an empty username and a valid password, when the user presses the login button, then an error message is displayed`() = runBlocking {
        viewModel.login(EMPTY_USERNAME, VALID_PASSWORD)
        verifyNoInteractions(repository)
        Assert.assertEquals(1, viewModel.inputErrors.size)
        Assert.assertEquals(InputError.EMPTY_USERNAME, viewModel.inputErrors.first())
        Assert.assertEquals(LoginResponse.INVALID_INPUT, viewModel.login.value)
    }

    @Test
    fun `given a valid username and an empty password, when the user presses the login button, then an error message is displayed`() = runBlocking {
        viewModel.login(VALID_USERNAME, EMPTY_PASSWORD)
        verifyNoInteractions(repository)
        Assert.assertEquals(1, viewModel.inputErrors.size)
        Assert.assertEquals(InputError.EMPTY_PASSWORD, viewModel.inputErrors.first())
        Assert.assertEquals(LoginResponse.INVALID_INPUT, viewModel.login.value)
    }

    @Test
    fun `given empty username and password, when the user presses the login button, then an error message is displayed`() = runBlocking {
        viewModel.login(EMPTY_USERNAME, EMPTY_PASSWORD)
        verifyNoInteractions(repository)
        Assert.assertEquals(2, viewModel.inputErrors.size)
        Assert.assertEquals(LoginResponse.INVALID_INPUT, viewModel.login.value)
    }
}
