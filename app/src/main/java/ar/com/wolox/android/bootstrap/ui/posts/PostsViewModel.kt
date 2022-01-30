package ar.com.wolox.android.bootstrap.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.network.util.RequestStatus
import ar.com.wolox.android.bootstrap.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostRepository,
) : ViewModel() {

    var view: PostsView? = null
        private set

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus>
        get() = _requestStatus

    fun toggleRequestStatus() {
        _requestStatus.apply {
            value = if (value != RequestStatus.Loading) {
                view?.showLoading()
                RequestStatus.Loading
            } else {
                view?.hideLoading()
                RequestStatus.Finished
            }
        }
    }

    fun onRequestFailed(error: Int) {
        view?.hideLoading()
        _requestStatus.value = RequestStatus.Failure(error)
    }

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    fun getPosts() {
        viewModelScope.launch {
            toggleRequestStatus()
            val result = postsRepository.getPosts()
            if (result.isSuccessful) {
                _posts.value = result.body()!!
                toggleRequestStatus()
            } else {
                onRequestFailed(result.code())
            }
        }
    }
}
