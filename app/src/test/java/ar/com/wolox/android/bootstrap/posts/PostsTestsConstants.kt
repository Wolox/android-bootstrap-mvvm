package ar.com.wolox.android.bootstrap.posts

import ar.com.wolox.android.bootstrap.model.Post

object PostsTestsConstants {
    val SUCCESSFUL_POSTS_RESPONSE = listOf(
        Post(
            userId = 1,
            id = 1,
            title = "Lorem ipsum",
            body = "Dolor sit amet"
        )
    )
}
