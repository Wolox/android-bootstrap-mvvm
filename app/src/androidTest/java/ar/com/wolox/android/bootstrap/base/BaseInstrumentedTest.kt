package ar.com.wolox.android.bootstrap.base

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

open class BaseInstrumentedTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }
}
