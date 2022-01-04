package ro.ubb.ideasmanager.core.auth

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ro.ubb.ideasmanager.core.Api

object RemoteAuthDataSource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/v1/auth/signin")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        return try {
            Result.Success(authService.login(user))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

