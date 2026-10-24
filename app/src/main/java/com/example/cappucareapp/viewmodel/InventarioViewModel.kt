package com.example.cappucareapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cappucareapp.model.Movimiento
import com.example.cappucareapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InventarioViewModel : ViewModel() {

    private val _listaProductos = MutableStateFlow<List<Producto>>(emptyList())
    val listaProductos: StateFlow<List<Producto>> = _listaProductos.asStateFlow()

    private val _listaMovimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val listaMovimientos: StateFlow<List<Movimiento>> = _listaMovimientos.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    // Estado para activar/desactivar el filtro de stock crítico
    private val _soloStockCritico = MutableStateFlow(false)
    val soloStockCritico: StateFlow<Boolean> = _soloStockCritico.asStateFlow()

    val productosFiltrados: StateFlow<List<Producto>> = combine(
        _listaProductos,
        _textoBusqueda,
        _soloStockCritico
    ) { lista, query, critico ->
        var resultado = lista

        // Filtrar por stock crítico si está activado
        if (critico) {
            resultado = resultado.filter { it.stockActual <= it.stockMinimo }
        }

        // Filtrar por texto de búsqueda
        if (query.isNotBlank()) {
            resultado = resultado.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.codigo.contains(query, ignoreCase = true) ||
                        it.categoria.contains(query, ignoreCase = true)
            }
        }

        resultado
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

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

    // Actualizado para recibir 6 argumentos (incluyendo ubicación)
    fun agregarProducto(codigo: String, nombre: String, categoria: String, stockInicial: Int, stockMin: Int, ubicacion: String) {
        val nuevoProducto = Producto(
            codigo = codigo,
            nombre = nombre,
            categoria = categoria,
            stockActual = stockInicial,
            unidadMedida = "Unidades",
            stockMinimo = stockMin,
            ubicacion = ubicacion,
            estado = "Activo"
        )
        _listaProductos.value = _listaProductos.value + nuevoProducto

        // Registrar movimiento de ingreso inicial
        registrarMovimientoInterno(codigo, nombre, "ENTRADA", stockInicial, "Ingreso por registro inicial")
    }

    private fun registrarMovimientoInterno(codigo: String, nombre: String, tipo: String, cantidad: Int, motivo: String) {
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val nuevoMovimiento = Movimiento(
            id = "M${System.currentTimeMillis()}",
            codigoProducto = codigo,
            nombreProducto = nombre,
            tipo = tipo,
            cantidad = cantidad,
            motivo = motivo,
            fecha = fechaActual
        )
        _listaMovimientos.value = listOf(nuevoMovimiento) + _listaMovimientos.value
    }

    fun actualizarBusqueda(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
    }

    fun alternarFiltroCritico() {
        _soloStockCritico.value = !_soloStockCritico.value
    }
}