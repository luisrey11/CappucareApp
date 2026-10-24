package com.example.cappucareapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cappucareapp.model.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleProducto(
    producto: Producto,
    onRegistrarMovimiento: (String, Int, Boolean) -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Insumo - Cappucare") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = producto.nombre, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Código: ${producto.codigo}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Categoría: ${producto.categoria}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Stock Actual: ${producto.stockActual} ${producto.unidadMedida}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Stock Mínimo (Alerta): ${producto.stockMinimo}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Ubicación: ${producto.ubicacion}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Estado: ${producto.estado}", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Acciones rápidas de inventario:", style = MaterialTheme.typography.titleMedium)

            // Botón para simular salida de stock (ej: venta o entrega)
            Button(
                onClick = { onRegistrarMovimiento(producto.codigo, 1, true) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Registrar Salida / Venta (-1 unidad)")
            }

            // Botón para simular ingreso de stock (ej: reposición)
            OutlinedButton(
                onClick = { onRegistrarMovimiento(producto.codigo, 5, false) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Registrar Ingreso / Reposición (+5 unidades)")
            }
        }
    }
}