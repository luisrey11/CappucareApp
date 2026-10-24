package com.example.cappucareapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroProducto(
    onGuardarProducto: (String, String, String, Int, Int, String) -> Unit,
    onVolver: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stockActual by remember { mutableStateOf("") }
    var stockMinimo by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    // Estados de error para validación visual por campo
    var errorCodigo by remember { mutableStateOf(false) }
    var errorNombre by remember { mutableStateOf(false) }
    var errorCategoria by remember { mutableStateOf(false) }
    var errorStockActual by remember { mutableStateOf(false) }
    var errorStockMinimo by remember { mutableStateOf(false) }
    var errorUbicacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Insumo Médico") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Ingrese los datos del insumo médico:",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it; errorCodigo = it.isBlank() },
                label = { Text("Código del producto (Ej: P005)") },
                isError = errorCodigo,
                supportingText = { if (errorCodigo) Text("El código no puede estar vacío", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; errorNombre = it.isBlank() },
                label = { Text("Nombre y descripción") },
                isError = errorNombre,
                supportingText = { if (errorNombre) Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it; errorCategoria = it.isBlank() },
                label = { Text("Categoría (Ej: Protección, Desinfección)") },
                isError = errorCategoria,
                supportingText = { if (errorCategoria) Text("La categoría es obligatoria", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = stockActual,
                onValueChange = { stockActual = it; errorStockActual = it.toIntOrNull() == null || it.toInt() < 0 },
                label = { Text("Cantidad / Stock Disponible") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorStockActual,
                supportingText = { if (errorStockActual) Text("Ingrese un número válido mayor o igual a 0", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = stockMinimo,
                onValueChange = { stockMinimo = it; errorStockMinimo = it.toIntOrNull() == null || it.toInt() < 0 },
                label = { Text("Nivel Mínimo de Existencias (Alerta)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorStockMinimo,
                supportingText = { if (errorStockMinimo) Text("Ingrese un stock mínimo válido", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it; errorUbicacion = it.isBlank() },
                label = { Text("Ubicación en Bodega (Ej: Estante A1)") },
                isError = errorUbicacion,
                supportingText = { if (errorUbicacion) Text("La ubicación es obligatoria", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    errorCodigo = codigo.isBlank()
                    errorNombre = nombre.isBlank()
                    errorCategoria = categoria.isBlank()
                    errorStockActual = stockActual.toIntOrNull() == null || stockActual.toInt() < 0
                    errorStockMinimo = stockMinimo.toIntOrNull() == null || stockMinimo.toInt() < 0
                    errorUbicacion = ubicacion.isBlank()

                    if (!errorCodigo && !errorNombre && !errorCategoria && !errorStockActual && !errorStockMinimo && !errorUbicacion) {
                        onGuardarProducto(
                            codigo,
                            nombre,
                            categoria,
                            stockActual.toInt(),
                            stockMinimo.toInt(),
                            ubicacion
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar e Ingresar Insumo")
            }
        }
    }
}