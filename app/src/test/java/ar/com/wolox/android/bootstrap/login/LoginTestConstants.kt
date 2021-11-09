package ar.com.wolox.android.bootstrap.login

import ar.com.wolox.android.bootstrap.model.User

object LoginTestConstants {
    const val VALID_USER_ID = 1
    const val VALID_USERNAME = "Bret"
    const val VALID_PASSWORD = "Password123"
    const val VALID_NAME = "Bret Harris"
    const val VALID_PHONE = "0352-4312"
    const val INVALID_USERNAME = "John"
    const val ERROR_RESPONSE = "{}"
    const val EMPTY_USERNAME = ""
    const val EMPTY_PASSWORD = ""
    val SUCCESSFUL_USER_LIST = listOf(User(VALID_USER_ID, VALID_NAME, VALID_USERNAME, VALID_PHONE))
}
