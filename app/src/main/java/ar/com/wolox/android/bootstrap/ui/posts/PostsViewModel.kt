package ar.com.wolox.android.bootstrap.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.repository.PostRepository
import ar.com.wolox.android.bootstrap.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostRepository,
) : BaseViewModel<Nothing>() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    fun getPosts() {
        viewModelScope.launch {
            toggleRequestStatus()
            val result = postsRepository.getPosts()
            if (result.isSuccessful) {
                _posts.value = result.body()!!
            } else {
            }
            toggleRequestStatus()
        }
    }
}
