package net.azarquiel.appdeliciasdelatierra.model

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

data class Producto (
    var idproducto: Int,
    var nombre: String,
    var descrpcion: String,
    var fecha: Date,
    var estado: String,
    var idcategoria: Int,
    var imagen: ByteArray

): Serializable

data class Categoria (
    var idcategoria: Int,
    var nombre: String

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
    val usuario: Usuario,
    val producto: Producto,
    val categoria: Categoria,
    val intercambio: Intercambio,
    val mensaje: Mensaje
)

