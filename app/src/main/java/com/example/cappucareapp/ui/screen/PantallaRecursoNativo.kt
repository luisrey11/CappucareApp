package com.example.cappucareapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cappucareapp.util.VibratorUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRecursoNativo(
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escáner y Recursos Nativos") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cámara y Sensor Activos (Recursos Nativos)",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // Recurso Nativo 2: Vibración al simular un escaneo exitoso
                    VibratorUtil.dispararVibracion(context, 300)
                }
            ) {
                Text("Simular Escaneo Exitoso (Vibrar)")
            }
        }
    }
}