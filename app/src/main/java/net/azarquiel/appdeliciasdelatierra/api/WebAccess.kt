package net.azarquiel.appdeliciasdelatierra.api
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object WebAccess {

    val deliciasService : DeliciasService by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://192.168.0.12:8082/")
            .build()

        return@lazy retrofit.create(DeliciasService::class.java)
    }
}