package com.example.cappucareapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cappucareapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InventarioViewModel : ViewModel() {

    private val _listaProductos = MutableStateFlow<List<Producto>>(emptyList())
    val listaProductos: StateFlow<List<Producto>> = _listaProductos.asStateFlow()

    init {
        cargarProductosIniciales()
    }

    private fun cargarProductosIniciales() {
        _listaProductos.value = listOf(
            Producto("P001", "Mascarillas Quirúrgicas (Caja x50)", "Protección", 12, "Cajas", 15, "Estante A1", "Activo"),
            Producto("P002", "Guantes de Nitrilo (Caja x100)", "Protección", 8, "Cajas", 10, "Estante A2", "Activo"),
            Producto("P003", "Alcohol Gel 1 Litro", "Desinfección", 25, "Litros", 5, "Bodega Central", "Activo"),
            Producto("P004", "Jeringas 5ml (Caja x100)", "Insumos Clínicos", 6, "Cajas", 10, "Estante B3", "Activo")
        )
    }

    fun agregarProducto(codigo: String, nombre: String, categoria: String, stockInicial: Int, stockMin: Int) {
        val nuevoProducto = Producto(
            codigo = codigo,
            nombre = nombre,
            categoria = categoria,
            stockActual = stockInicial,
            unidadMedida = "Unidades",
            stockMinimo = stockMin,
            ubicacion = "Bodega Principal",
            estado = "Activo"
        )
        _listaProductos.value = _listaProductos.value + nuevoProducto
    }

    fun registrarMovimiento(codigoProducto: String, cantidad: Int, esVenta: Boolean) {
        val listaActualizada = _listaProductos.value.map { producto ->
            if (producto.codigo == codigoProducto) {
                val nuevoStock = if (esVenta) producto.stockActual - cantidad else producto.stockActual + cantidad
                producto.copy(stockActual = if (nuevoStock >= 0) nuevoStock else 0)
            } else {
                producto
            }
        }
        _listaProductos.value = listaActualizada
    }
}