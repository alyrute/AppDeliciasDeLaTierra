package net.azarquiel.appdeliciasdelatierra.api

import android.util.Log
import androidx.lifecycle.LiveData
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Respuesta
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import java.io.IOException

class MainRepository() {
    val service = WebAccess.deliciasService

    suspend fun getCategorias(): List<Categoria> {
        val webResponse = service.getCategorias().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }

    suspend fun getProductosPorCategoria(idcategoria:Int): List<Producto> {
        val webResponse = service.getProductosPorCategoria(idcategoria).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.producto
        }
        return emptyList()
    }

    suspend fun getProductos(idusuario:Int): Producto? {
        val webResponse = service.getProductos(idusuario).await()
        return if (webResponse.isSuccessful) {
            webResponse.body()
        } else {
            null
        }
    }
    suspend fun saveProducto(producto: Producto): Producto? {
        val webResponse = service.saveProducto(producto).await()
        if (webResponse.isSuccessful) {
            val productoResponse = webResponse.body()
            return productoResponse
        }
        return null
    }

    suspend fun getUsuarioByProducto(idproducto:Int): Usuario? {
        val webResponse = service.getUsuarioByProducto(idproducto).await()
        if (webResponse.isSuccessful) {
            val usuario = webResponse.body()
            Log.d("coño" ,"$usuario")
            return usuario
        }
        return null
    }

    suspend fun getIntercambios(): List<Intercambio> {
        val webResponse = service.getIntercambios().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }

    suspend fun insertarIntercambio(intercambio: Intercambio): Intercambio? {
        val webResponse = service.insertarIntercambio(intercambio).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

    suspend fun getLogin(email: String, password: String): Usuario? {
        val webResponse = service.getLogin(email, password).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

    suspend fun getUsuarioById(idusuario: Int): Usuario? {
        val webResponse = service.getUsuarioById(idusuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }


    suspend fun register(usuario: Usuario): Usuario? {
        val webResponse = service.register(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

/*
    suspend fun getMensajes(): List<Mensaje>? {
        val webResponse = service.getMensajes().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }*/

    suspend fun getMensajesByIdProducto(idproducto: Int, senderid: Int, receiverid: Int): List<Mensaje> {
        val mensajesRecibidosResponse = service.obtenerMensajesPorProducto(idproducto, senderid, receiverid).await()

        return if (mensajesRecibidosResponse.isSuccessful) {
            mensajesRecibidosResponse.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun insertarMensaje(mensaje: Mensaje): Mensaje? {
        return try {
            val webResponse = service.insertarMensaje(mensaje).await()
            Log.d("coño" , "$mensaje")
            if (webResponse.isSuccessful) {
                webResponse.body()

            } else {

                null
            }
        } catch (e: IOException) {
            // Manejar la excepción de red
            null
        }
    }




}


