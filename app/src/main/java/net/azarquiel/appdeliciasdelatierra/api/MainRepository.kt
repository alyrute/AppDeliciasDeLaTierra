package net.azarquiel.appdeliciasdelatierra.api

import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario


class MainRepository() {
    val service = WebAccess.deliciasService

    suspend fun getCategorias(): List<Categoria> {
        val webResponse = service.getCategorias().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }
    suspend fun insertarCategoria(categoria: Categoria): Categoria? {
        val webResponse = service.insertarCategoria(categoria).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
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

    suspend fun insertarMensaje(mensaje: Mensaje): Mensaje? {
        val webResponse = service.insertarMensaje(mensaje).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

    suspend fun getProductos(): List<Producto>? {
        val webResponse = service.getProductos().await()
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

    suspend fun getLogin(email: String, password: String): Usuario? {
        val webResponse = service.getLogin(email, password).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

    suspend fun getUsuarios(): List<Usuario>? {
        val webResponse = service.getUsuarios().await()
        return if (webResponse.isSuccessful) {
            webResponse.body()
        } else {
            null
        }
    }
    suspend fun register(usuario: Usuario): Usuario? {
        val webResponse = service.register(usuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }


    suspend fun enviarMensaje(mensaje: Mensaje): Mensaje? {
        val webResponse = service.enviarMensaje(mensaje).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()
        }
        return null
    }

    suspend fun recibirMensajes(idUsuario: Int): List<Mensaje> {
        val webResponse = service.recibirMensajes(idUsuario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }




}

