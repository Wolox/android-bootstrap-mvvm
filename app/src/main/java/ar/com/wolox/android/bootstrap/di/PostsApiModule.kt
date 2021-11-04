package ar.com.wolox.android.bootstrap.di

import ar.com.wolox.android.bootstrap.network.PostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class PostsApiModule {

    @Provides
    fun providePostsApi(retrofit: Retrofit): PostService =
        retrofit.create(PostService::class.java)
}
