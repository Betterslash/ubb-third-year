package ro.ubb.ideasmanager.core.service
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ro.ubb.ideasmanager.core.Api
import ro.ubb.ideasmanager.core.TokenInterceptor
import ro.ubb.ideasmanager.model.IdeaModel

object IdeaService {
    private const val URL = "http://192.168.1.4:8080/"
    val tokenInterceptor = TokenInterceptor()
    interface Service {
        @GET("/api/v1/ideas")
        suspend fun getAll(): List<IdeaModel>

        @GET("/api/v1/ideas/{id}")
        suspend fun read(@Path("id") ideaId : String) : IdeaModel

        @Headers("Content-Type: application/json")
        @POST("/api/v1/ideas")
        suspend fun create(@Body item: IdeaModel): IdeaModel

        @Headers("Content-Type: application/json")
        @PUT("/api/v1/ideas/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: IdeaModel): IdeaModel

    }

    val service: Service = Api.retrofit.create(Service::class.java)
}