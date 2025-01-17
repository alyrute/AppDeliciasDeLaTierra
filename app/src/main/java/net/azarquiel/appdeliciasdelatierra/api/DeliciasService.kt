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

    @GET("productos/{idusuario}")
    fun getProductos(@Path("idusuario") idproducto: Int): Deferred<Response<Respuesta>>

    @POST("producto")
    fun saveProducto(@Body producto: Producto): Deferred<Response<Producto>>

    @GET("producto/{idproducto}/usuario")
    fun getUsuarioByProducto(@Path("idproducto") idproducto: Int): Deferred<Response<Usuario>>

    @GET("login")
    fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String): Deferred<Response<Usuario>>

    @GET("usuario/{idusuario}")
    fun getUsuarioById(@Path("idusuario") id: Int): Deferred<Response<Usuario>>

    @POST("register")
    fun register(@Body usuario: Usuario): Deferred<Response<Usuario>>

    @GET("mensajes/producto/{idproducto}/")
    fun obtenerMensajesPorProducto(
        @Path("idproducto") idProducto: Int, @Query("senderid") senderId: Int, @Query("receiverid") receiverId: Int): Deferred<Response<List<Mensaje>>>

    @POST("mensaje")
    fun insertarMensaje(@Body mensaje: Mensaje): Deferred<Response<Mensaje>>


    @DELETE("producto/{idproducto}")
    fun deleteProduct(@Path("idproducto") productId: Int): Deferred<Response<Void>>



}
