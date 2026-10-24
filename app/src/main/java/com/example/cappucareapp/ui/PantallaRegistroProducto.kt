package com.example.cappucareapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroProducto(
    onGuardarProducto: (String, String, String, Int, Int) -> Unit,
    onVolver: () -> Unit
) {
    // Estados para los campos del formulario
    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stockInicial by remember { mutableStateOf("") }
    var stockMinimo by remember { mutableStateOf("") }

    // Estados para controlar errores de validación
    var errorCodigo by remember { mutableStateOf(false) }
    var errorNombre by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Nuevo Insumo") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Campo Código
            OutlinedTextField(
                value = codigo,
                onValueChange = {
                    codigo = it
                    errorCodigo = it.isBlank()
                },
                label = { Text("Código del Producto (Ej: P005)") },
                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                isError = errorCodigo,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            if (errorCodigo) {
                Text("El código no puede estar vacío", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorNombre = it.isBlank()
                },
                label = { Text("Nombre y Descripción") },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                isError = errorNombre,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            if (errorNombre) {
                Text("El nombre no puede estar vacío", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Campo Categoría
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría (Ej: Protección, Insumos)") },
                leadingIcon = { Icon(Icons.Default.List, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Stock Inicial
            OutlinedTextField(
                value = stockInicial,
                onValueChange = { stockInicial = it },
                label = { Text("Stock Inicial") },
                leadingIcon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Stock Mínimo
            OutlinedTextField(
                value = stockMinimo,
                onValueChange = { stockMinimo = it },
                label = { Text("Stock Mínimo (Alerta)") },
                leadingIcon = { Icon(Icons.Default.Warning, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar con validación lógica previa
            Button(
                onClick = {
                    errorCodigo = codigo.isBlank()
                    errorNombre = nombre.isBlank()

                    if (!errorCodigo && !errorNombre) {
                        val stockInt = stockInicial.toIntOrNull() ?: 0
                        val minStockInt = stockMinimo.toIntOrNull() ?: 5
                        onGuardarProducto(codigo, nombre, categoria.ifBlank { "General" }, stockInt, minStockInt)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Producto")
            }
        }
    }
}