package net.azarquiel.appdeliciasdelatierra.viewmodel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.appdeliciasdelatierra.api.MainRepository
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Intercambio
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario

class MainViewModel : ViewModel() {
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val usuario: MutableLiveData<Usuario?> = MutableLiveData()


    private val repository = MainRepository()

    fun getCategorias() = viewModelScope.launch { repository.getCategorias() }
    fun insertarCategoria(categoria: Categoria) = viewModelScope.launch {
        repository.insertarCategoria(categoria)
    }

    fun getIntercambios() = viewModelScope.launch { repository.getIntercambios() }
    fun insertarIntercambio(intercambio: Intercambio) = viewModelScope.launch {
        repository.insertarIntercambio(intercambio)
    }

    fun insertarMensaje(mensaje: Mensaje) = viewModelScope.launch {
        repository.insertarMensaje(mensaje)
    }


    fun getProductos() = viewModelScope.launch { repository.getProductos() }
    fun insertarProducto(producto: Producto) = viewModelScope.launch {
        repository.insertarProducto(producto)
    }

    fun getLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.getLogin(email, password)
            usuario.value = result
        }
    }



    fun register(usuario: Usuario) = viewModelScope.launch { repository.register(usuario) }
    fun enviarMensaje(mensaje: Mensaje) = viewModelScope.launch {
        repository.enviarMensaje(mensaje)
    }

    fun recibirMensajes(idUsuario: Int) = viewModelScope.launch { repository.recibirMensajes(idUsuario) }
}
