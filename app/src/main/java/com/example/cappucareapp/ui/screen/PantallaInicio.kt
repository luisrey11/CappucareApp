package com.example.cappucareapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cappucareapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    onNavegarALista: () -> Unit,
    onNavegarARegistro: () -> Unit,
    onNavegarACamara: () -> Unit,
    onNavegarAMovimientos: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cappucare - Gestión Médica") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_corazon),
                contentDescription = "Logotipo de CappuCare",
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "¡Bienvenido a Cappucare!",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Sistema de Control e Inventario de Insumos Médicos",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Selecciona una opción:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OpcionMenuCard(
                titulo = "Ver Insumos Médicos",
                subtitulo = "Consulta el stock disponible y las alertas de estado",
                icono = Icons.Default.Inventory,
                onClick = onNavegarALista
            )

            Spacer(modifier = Modifier.height(10.dp))

            OpcionMenuCard(
                titulo = "Registrar Nuevo Insumo",
                subtitulo = "Añade nuevos productos mediante el formulario",
                icono = Icons.Default.Add,
                onClick = onNavegarARegistro
            )

            Spacer(modifier = Modifier.height(10.dp))

            OpcionMenuCard(
                titulo = "Historial de Movimientos",
                subtitulo = "Revisa entradas, salidas y ventas registradas",
                icono = Icons.Default.History,
                onClick = onNavegarAMovimientos
            )

            Spacer(modifier = Modifier.height(10.dp))

            OpcionMenuCard(
                titulo = "Escáner con Cámara",
                subtitulo = "Accede a la cámara del dispositivo para escanear",
                icono = Icons.Default.CameraAlt,
                onClick = onNavegarACamara
            )
        }
    }
}

@Composable
fun OpcionMenuCard(
    titulo: String,
    subtitulo: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icono,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}