package ar.com.wolox.android.bootstrap.base

import ar.com.wolox.android.bootstrap.di.ServicesModules
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseInstrumentedTest() {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    protected var service: MockWebServer = MockWebServer()

    @Before
    fun setUp() {
        service.start(57808)
        ServicesModules.BASE_URL = service.url("/").toString()
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        service.shutdown()
    }
}
