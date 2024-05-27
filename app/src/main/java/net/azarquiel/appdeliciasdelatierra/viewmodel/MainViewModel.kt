package net.azarquiel.appdeliciasdelatierra.viewmodel

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
    val mensajeLiveData = MutableLiveData<Mensaje?>()

    private val repository = MainRepository()

    fun getCategoria(): MutableLiveData<List<Categoria>> {
        val categorias = MutableLiveData<List<Categoria>>()
        GlobalScope.launch(Main) {
            categorias.value = repository.getCategorias()
        }
        return categorias
    }
    fun getProductosPorCategoria(idcategoria:Int): MutableLiveData<List<Producto>> {
        val productos = MutableLiveData<List<Producto>>()
        GlobalScope.launch(Main) {
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

    fun getUsuarioByProducto(idproducto: Int): MutableLiveData<Usuario?> {
        val respuesta = MutableLiveData<Usuario?>()
        GlobalScope.launch(Main) {
            respuesta.value = repository.getUsuarioByProducto(idproducto)
        }
        return respuesta
    }

    fun getIntercambios() = viewModelScope.launch { repository.getIntercambios() }
    fun insertarIntercambio(intercambio: Intercambio) = viewModelScope.launch {
        repository.insertarIntercambio(intercambio)
    }



    fun getProductos(idusuario:Int): MutableLiveData<Producto?> {
        val productos = MutableLiveData<Producto?>()
        GlobalScope.launch(Main) {
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
        GlobalScope.launch(Dispatchers.Main) {
            usuarioResponse.value = repository.register(usuario)
        }
        return usuarioResponse
    }

    fun getMensajes(): LiveData<List<Mensaje>> {
        val mensajes = MutableLiveData<List<Mensaje>>()
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getMensajes()
            mensajes.postValue(data)
        }
        return mensajes
    }

    fun getMensajesByUsuario(idUsuario: Int): MutableLiveData<List<Mensaje>> {
        val mensajes = MutableLiveData<List<Mensaje>>()
        GlobalScope.launch(Dispatchers.Main) {
            mensajes.value = repository.getMensajesByUsuario(idUsuario)
        }
        return mensajes
    }

    fun insertarMensaje(mensaje: Mensaje) = viewModelScope.launch {
        val mensajeEnviado = repository.insertarMensaje(mensaje)
        mensajeLiveData.postValue(mensajeEnviado)
    }



}