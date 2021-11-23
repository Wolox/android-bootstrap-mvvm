package ar.com.wolox.android.bootstrap.network.util

sealed class RequestStatus {
    object Finished : RequestStatus()
    object Loading : RequestStatus()
    class Failure(val error: Int) : RequestStatus()
}
