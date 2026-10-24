package com.example.cappucareapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRecursoNativo(
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    var tienePermisoCamara by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Solicitador de permisos de cámara en tiempo de ejecución
    val lanzadorPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        tienePermisoCamara = concedido
    }

    var estadoEscaneo by remember { mutableStateOf("Presione el botón para activar la cámara y escanear insumo") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escáner de Insumos - Cámara") },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = estadoEscaneo,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (tienePermisoCamara) {
                Button(
                    onClick = {
                        // Simulación de lectura exitosa de código QR / de barras médico
                        estadoEscaneo = "✅ Insumo escaneado con éxito: [Código P001 - Mascarillas Quirúrgicas]"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Escanear Código de Producto")
                }
            } else {
                Button(
                    onClick = {
                        lanzadorPermiso.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solicitar Permiso de Cámara")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⚠️ Se requiere permiso de cámara para lectura de códigos de barra o QR de insumos médicos.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}