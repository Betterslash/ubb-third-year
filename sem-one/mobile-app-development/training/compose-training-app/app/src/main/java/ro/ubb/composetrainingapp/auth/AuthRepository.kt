package ro.ubb.composetrainingapp.auth

import android.util.Log
import ro.ubb.composetrainingapp.core.RetrofitBuilder
import ro.ubb.composetrainingapp.core.TAG

object AuthRepository {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        RetrofitBuilder.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<TokenHolder> {
        val user = User(username, password)
        val result = AuthDatasource.login(user)
        if (result.isSuccess) {
            setLoggedInUser(user, result.getOrThrow())
        }
        return result
    }

    private fun setLoggedInUser(user: User, tokenHolder: TokenHolder) {
        AuthRepository.user = user
        RetrofitBuilder.tokenInterceptor.token = tokenHolder.token
    }
}