package net.azarquiel.appdeliciasdelatierra.api
import kotlinx.coroutines.Deferred
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Login
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import retrofit2.Response
import retrofit2.http.*
interface DeliciasService {

    @GET("categorias")
    fun getCategorias(): Deferred<Response<List<Categoria>>>

    @POST("categoria")
    fun insertarCategoria(@Body categoria: Categoria): Deferred<Response<Categoria>>

    @GET("intercambios")
    fun getIntercambios(): Deferred<Response<List<Intercambio>>>

    @POST("intercambio")
    fun insertarIntercambio(@Body intercambio: Intercambio): Deferred<Response<Intercambio>>

    @POST("mensaje")
    fun insertarMensaje(@Body mensaje: Mensaje): Deferred<Response<Mensaje>>

    @GET("productos")
    fun getProductos(): Deferred<Response<List<Producto>>>

    @POST("producto")
    fun insertarProducto(@Body producto: Producto): Deferred<Response<Producto>>

    @POST("login")
    fun login(@Body login: Login): Deferred<Response<String>>

    @POST("register")
    fun register(@Body usuario: Usuario): Deferred<Response<String>>

    @POST("mensaje")
    fun enviarMensaje(@Body mensaje: Mensaje): Deferred<Response<Mensaje>>

    @GET("mensajes/{idUsuario}")
    fun recibirMensajes(@Path("idUsuario") idUsuario: Int): Deferred<Response<List<Mensaje>>>




}
