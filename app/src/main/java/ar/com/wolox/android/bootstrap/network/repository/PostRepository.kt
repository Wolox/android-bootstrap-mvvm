package ar.com.wolox.android.bootstrap.network.repository

import ar.com.wolox.android.bootstrap.network.service.PostService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor(private val postsService: PostService) {

    suspend fun getPosts() = withContext(Dispatchers.IO) { postsService.getPosts() }
}
