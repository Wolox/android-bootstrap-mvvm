package ar.com.wolox.android.bootstrap.base

import ar.com.wolox.android.bootstrap.ui.base.BaseView
import ar.com.wolox.android.bootstrap.ui.base.BaseViewModel
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.reflect.ParameterizedType

@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTest<V: BaseView, M: BaseViewModel<V>> {

    protected lateinit var viewModel: M
    protected lateinit var view: V

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = getViewModelInstance()
        view = mock(getViewClass())
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewClass(): Class<V> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<V>
    }

    abstract fun getViewModelInstance(): M
}
