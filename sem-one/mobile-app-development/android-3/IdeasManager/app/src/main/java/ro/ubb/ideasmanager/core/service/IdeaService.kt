package ro.ubb.ideasmanager.core.service
import retrofit2.http.*
import ro.ubb.ideasmanager.core.Api
import ro.ubb.ideasmanager.core.TokenInterceptor
import ro.ubb.ideasmanager.model.IdeaModel

object IdeaService {
    interface Service {
        @GET("/api/v1/ideas")
        suspend fun getAll(): List<IdeaModel>

        @GET("/api/v1/ideas/{id}")
        suspend fun read(@Path("id") ideaId : String) : IdeaModel

        @Headers("Content-Type: application/json")
        @POST("/api/v1/ideas")
        suspend fun create(@Body idea: IdeaModel): IdeaModel

        @Headers("Content-Type: application/json")
        @PUT("/api/v1/ideas/{id}")
        suspend fun update(@Path("id") ideaId: String, @Body item: IdeaModel): IdeaModel

        @Headers("Content-Type: application/json")
        @DELETE("/api/v1/ideas/{id}")
        suspend fun delete(@Path("id")ideaId: String): IdeaModel

        @Headers("Content-Type: application/json")
        @POST("/api/v1/ideas/sync")
        suspend fun sync(@Body ideas: List<IdeaModel>): List<IdeaModel>
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}