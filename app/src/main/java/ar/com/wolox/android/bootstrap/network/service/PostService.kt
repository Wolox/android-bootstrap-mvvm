package ar.com.wolox.android.bootstrap.network.service

import ar.com.wolox.android.bootstrap.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>
}
