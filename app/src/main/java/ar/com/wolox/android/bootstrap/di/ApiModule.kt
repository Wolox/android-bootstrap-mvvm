package ar.com.wolox.android.bootstrap.di

import ar.com.wolox.android.bootstrap.network.service.PostService
import ar.com.wolox.android.bootstrap.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun providePostsService(retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)

    @Provides
    fun provideUsersService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}
