package com.example.cappucareapp.model

data class Producto(
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val stockActual: Int,
    val unidadMedida: String,
    val stockMinimo: Int,
    val ubicacion: String,
    val estado: String
)