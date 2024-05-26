package net.azarquiel.appdeliciasdelatierra.api
import kotlinx.coroutines.Deferred
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Respuesta
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import retrofit2.Response
import retrofit2.http.*
interface DeliciasService {

    @GET("categorias")
    fun getCategorias(): Deferred<Response<List<Categoria>>>

     @GET("categoria/{idcategoria}/producto")
    fun getProductosPorCategoria(@Path("idcategoria") idcategoria: Int): Deferred<Response<Respuesta>>

    @GET("producto")
    suspend fun getProductos(@Query("idusuario") idusuario: Int): Deferred<Response<Producto>>

    @POST("producto")
    fun saveProducto(@Body producto: Producto): Deferred<Response<Producto>>

    @GET("producto/{idproducto}/usuario")
    fun getUsuarioByProducto(@Path("idproducto") idproducto: Int): Deferred<Response<Usuario>>

    @GET("intercambios")
    fun getIntercambios(): Deferred<Response<List<Intercambio>>>

    @POST("intercambio")
    fun insertarIntercambio(@Body intercambio: Intercambio): Deferred<Response<Intercambio>>

    @GET("login")
    fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String): Deferred<Response<Usuario>>

    @GET("usuarios")
    fun getUsuarios(): Deferred<Response<List<Usuario>>>

    @POST("register")
    fun register(@Body usuario: Usuario): Deferred<Response<Usuario>>

    @POST("mensaje")
    fun enviarMensaje(@Body mensaje: Mensaje): Deferred<Response<Mensaje>>

    @GET("mensajes/{idUsuario}")
    fun recibirMensajes(@Path("idUsuario") idUsuario: Int): Deferred<Response<List<Mensaje>>>

    @POST("mensaje")
    fun insertarMensaje(@Body mensaje: Mensaje): Deferred<Response<Mensaje>>


}
