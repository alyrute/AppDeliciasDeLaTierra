package net.azarquiel.appdeliciasdelatierra.model

import java.io.Serializable

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
    val idproducto: Int? = null,
    var usuario: Usuario,
    var nombre: String,
    var descripcion: String,
    var fecha: String,
    var estado: String,
    var categoria: Categoria,
    var imagen: String,
    ): Serializable


data class Categoria (
    var idcategoria: Int,
    var nombre: String,
    var imagen:String

): Serializable


data class Intercambio (
    var idintercamnbio: Int,
    var idusuario1: Int,
    var idusuario2: Int,
    var idproducto1: Int,
    var idproducto2: Int,

): Serializable

data class Mensaje(
    val senderid: Int,
    val receiverid: Int,
    val texto: String,
    val fecha: String,
    val leido: Boolean,
    val idproducto: Int

    ): Serializable

data class Respuesta (
    var usuario: Usuario?,
    var producto: List<Producto>,
    var categoria: Categoria,
    var intercambio: Intercambio,
    var mensaje: Mensaje,

)

