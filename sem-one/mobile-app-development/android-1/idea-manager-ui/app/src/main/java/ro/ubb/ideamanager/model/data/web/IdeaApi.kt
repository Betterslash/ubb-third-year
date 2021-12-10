package ro.ubb.ideamanager.model.data.web

import retrofit2.http.*
import ro.ubb.ideamanager.model.idea.Idea
import ro.ubb.ideamanager.shared.Api

object IdeaApi {
    interface Service{
        @GET("api/v1/ideas")
        suspend fun getAll() : List<Idea>

        @GET("/api/item/{id}")
        suspend fun read(@Path("id") ideaId: String): Idea;

        @Headers("Content-Type: application/json")
        @POST("/api/item")
        suspend fun create(@Body idea: Idea): Idea

        @Headers("Content-Type: application/json")
        @PUT("/api/item/{id}")
        suspend fun update(@Path("id") ideaId: String, @Body idea: Idea): Idea
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}