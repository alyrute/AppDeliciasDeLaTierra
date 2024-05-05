package net.azarquiel.appdeliciasdelatierra.api
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object WebAccess {

    val deliciasService : DeliciasService by lazy {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://localhost:8082/")
            .build()

        return@lazy retrofit.create(DeliciasService::class.java)
    }
}