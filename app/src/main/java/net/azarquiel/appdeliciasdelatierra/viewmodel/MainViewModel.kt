package net.azarquiel.appdeliciasdelatierra.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.appdeliciasdelatierra.api.MainRepository
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Respuesta
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main


class MainViewModel : ViewModel() {

    val usuario: MutableLiveData<Usuario?> = MutableLiveData()
    private val _mensajeLiveData = MutableLiveData<Mensaje?>()
    val mensajeLiveData: LiveData<Mensaje?> = _mensajeLiveData

    private val repository = MainRepository()

    fun getCategoria(): MutableLiveData<List<Categoria>> {
        val categorias = MutableLiveData<List<Categoria>>()
        viewModelScope.launch(Main) {
            categorias.value = repository.getCategorias()
        }
        return categorias
    }
    fun getProductosPorCategoria(idcategoria:Int): MutableLiveData<List<Producto>> {
        val productos = MutableLiveData<List<Producto>>()
        viewModelScope.launch(Main) {
            productos.value = repository.getProductosPorCategoria(idcategoria)
        }
        return productos
    }

    fun saveProducto(producto: Producto): MutableLiveData<Producto> {
        val productoResponse = MutableLiveData<Producto>()
        viewModelScope.launch {
            productoResponse.value = repository.saveProducto(producto)
        }
        return productoResponse
    }

    fun getUsuarioById(idusuario: Int): MutableLiveData<Usuario>  {
        val usuarioResponse = MutableLiveData<Usuario>()
        viewModelScope.launch {
            usuarioResponse.value = repository.getUsuarioById(idusuario)
        }
        return usuarioResponse
    }

    fun getProductos(idusuario: Int): MutableLiveData<List<Producto>> {
        val productos = MutableLiveData<List<Producto>>()
        viewModelScope.launch {
            productos.value = repository.getProductos(idusuario)

        }
        return productos
    }

    fun getLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getLogin(email, password)
            usuario.value = result
        }
    }

    fun register (usuario: Usuario):MutableLiveData<Usuario> {
        val usuarioResponse= MutableLiveData<Usuario>()
        viewModelScope.launch(Dispatchers.Main) {
            usuarioResponse.value = repository.register(usuario)
        }
        return usuarioResponse
    }

    fun getMensajesByIdProducto(idproducto: Int, senderid: Int, receiverid: Int): LiveData<List<Mensaje>> {
        val mensajesLiveData = MutableLiveData<List<Mensaje>>()
        viewModelScope.launch {
            val mensajes = repository.getMensajesByIdProducto(idproducto, senderid, receiverid)
            mensajesLiveData.postValue(mensajes)
        }

        return mensajesLiveData
    }

    suspend fun insertarMensaje(mensaje: Mensaje) {
        repository.insertarMensaje(mensaje)
    }

    fun deleteProduct(productId: Int): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repository.deleteProduct(productId)
        }
        return result
    }



}