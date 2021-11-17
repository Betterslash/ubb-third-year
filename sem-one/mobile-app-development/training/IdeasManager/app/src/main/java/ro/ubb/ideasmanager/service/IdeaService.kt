package ro.ubb.ideasmanager.service
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ro.ubb.ideasmanager.model.IdeaModel

object IdeaService {
    private const val URL = "http://192.168.1.4:8080/"

    interface Service {
        @GET("/api/v1/ideas")
        suspend fun getAll(): List<IdeaModel>
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}