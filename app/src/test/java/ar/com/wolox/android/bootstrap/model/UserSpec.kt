package ar.com.wolox.android.bootstrap.model

import ar.com.wolox.android.bootstrap.UnitTestsConstants
import org.junit.Assert
import org.junit.Test

class UserSpec {

    @Test
    fun userSpec() {
        with(UnitTestsConstants) {
            val user = User(MOCK_INT, MOCK_STRING, MOCK_STRING, MOCK_STRING)
            Assert.assertEquals(MOCK_INT, user.userId)
            Assert.assertEquals(MOCK_STRING, user.name)
            Assert.assertEquals(MOCK_STRING, user.username)
            Assert.assertEquals(MOCK_STRING, user.phone)
        }
    }
}
