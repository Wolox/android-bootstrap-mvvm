package ar.com.wolox.android.bootstrap.di

import ar.com.wolox.android.bootstrap.repository.PostRepository
import ar.com.wolox.android.bootstrap.ui.posts.PostsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class PostsFragmentModule {

    @Provides
    fun postsViewModelProvider(postRepository: PostRepository) = PostsViewModel(postRepository)
}
