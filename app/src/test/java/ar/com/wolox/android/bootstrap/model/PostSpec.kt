package ar.com.wolox.android.bootstrap.model

import ar.com.wolox.android.bootstrap.UnitTestsConstants
import org.junit.Assert
import org.junit.Test

class PostSpec {

    @Test
    fun postSpec() {
        with(UnitTestsConstants) {
            val post = Post(MOCK_INT, MOCK_INT, MOCK_STRING, MOCK_STRING)
            Assert.assertEquals(MOCK_INT, post.id)
            Assert.assertEquals(MOCK_INT, post.userId)
            Assert.assertEquals(MOCK_STRING, post.title)
            Assert.assertEquals(MOCK_STRING, post.body)
        }
    }
}
