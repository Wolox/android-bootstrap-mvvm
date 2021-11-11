package ar.com.wolox.android.bootstrap.network.service

import ar.com.wolox.android.bootstrap.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}
