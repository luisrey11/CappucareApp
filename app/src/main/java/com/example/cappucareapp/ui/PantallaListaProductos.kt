package com.example.cappucareapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cappucareapp.model.Producto

@Composable
fun PantallaListaProductos(
    productos: List<Producto>,
    onNavegarARegistro: () -> Unit,
    onNavegarACamara: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cappucare - Insumos",
                        style = MaterialTheme.typography.titleLarge
                    )
                    // Botón superior para acceder al recurso nativo (Cámara / Escáner)
                    IconButton(onClick = onNavegarACamara) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Escanear con Cámara")
                    }
                }
            }
        },
        floatingActionButton = {
            // Botón flotante para ir al formulario de registro
            FloatingActionButton(onClick = onNavegarARegistro) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Insumo")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(productos, key = { it.codigo }) { producto ->
                // Animación de aparición fluida para cada tarjeta
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                            slideInVertically(animationSpec = tween(durationMillis = 500)) { it / 2 }
                ) {
                    TarjetaProducto(producto = producto)
                }
            }
        }
    }
}

@Composable
fun TarjetaProducto(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Código: ${producto.codigo} | Categoría: ${producto.categoria}")
            Text(text = "Disponibles: ${producto.stockActual} ${producto.unidadMedida}")
            Text(text = "Ubicación: ${producto.ubicacion}")

            // Alerta visual de stock crítico
            if (producto.stockActual <= producto.stockMinimo) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⚠️ Stock crítico - Requiere reposición",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}