package net.azarquiel.appdeliciasdelatierra.model

import android.util.Base64
import java.io.Serializable
import java.util.Date

data class Usuario (
    var idusuario: Int? =null,
    var nombre: String,
    var apellidos: String,
    var email: String,
    var password: String,
    var poblacion: String,
    var provincia: String

): Serializable

data class Producto(
    var nombre: String,
    var descripcion: String,
    var fecha: String,
    var estado: String,
    var idcategoria: Int,
    var imagen: String,
    var idusuario: Int,
    ): Serializable{


    fun getImagenByteArray(): ByteArray {
        return Base64.decode(imagen, Base64.DEFAULT)
    }
    }

data class Categoria (
    var idcategoria: Int,
    var nombre: String,
    var imagen:String,

): Serializable


data class Intercambio (
    var idintercamnbio: Int,
    var idusuario1: Int,
    var idusuario2: Int,
    var idproducto1: Int,
    var idproducto2: Int,

): Serializable

data class Mensaje (
    var idintercambio: Int,
    var idusuario1: Int,
    var idusuario2: Int,
    var texto: String,
    var fecha: Date,

    ): Serializable

data class Respuesta (
    var usuario: Usuario,
    var producto: Producto,
    var categoria: Categoria,
    var intercambio: Intercambio,
    var mensaje: Mensaje,

)

