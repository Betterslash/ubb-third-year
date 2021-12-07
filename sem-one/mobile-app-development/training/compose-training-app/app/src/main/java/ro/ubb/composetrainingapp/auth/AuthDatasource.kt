package ro.ubb.composetrainingapp.auth

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ro.ubb.composetrainingapp.core.RetrofitBuilder

object AuthDatasource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/v1/auth/signin")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService = RetrofitBuilder.retrofit.create(AuthService::class.java)

    suspend fun login(user: User) : Result<TokenHolder>{
        return try {
            Result.success(authService.login(user = user))
        }catch (e : Exception){
            Result.failure(e)
        }
    }
}