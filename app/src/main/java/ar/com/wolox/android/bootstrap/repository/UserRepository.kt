package ar.com.wolox.android.bootstrap.repository

import ar.com.wolox.android.bootstrap.network.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserService
) {

    suspend fun getUsers() = withContext(Dispatchers.IO) {
        service.getUsers()
    }
}
