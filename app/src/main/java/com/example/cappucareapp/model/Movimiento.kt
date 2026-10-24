package com.example.cappucareapp.model

data class Movimiento(
    val id: String,
    val codigoProducto: String,
    val nombreProducto: String,
    val tipo: String, // "ENTRADA" o "SALIDA"
    val cantidad: Int,
    val motivo: String, // Ej: "Compra a proveedor", "Venta a cliente", "Merma"
    val fecha: String
)