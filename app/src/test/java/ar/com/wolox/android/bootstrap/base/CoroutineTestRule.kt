package ar.com.wolox.android.bootstrap.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * If a test method is annotated with this, then the [CoroutineTestRule] will be executed.
 * Use it when [CoroutineTestRule.runOnAllTests] is false.
 */
annotation class CoroutineTest

/**
 * A Junit Test Rule that allows to use Coroutines main dispatcher on a test.
 * If [runOnAllTests] is false then all tests will have this configuration,
 * otherwise just those that have [CoroutineTest] annotation.
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
