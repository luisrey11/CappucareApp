package com.example.cappucareapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cappucareapp.ui.screen.PantallaInicio
import com.example.cappucareapp.ui.screen.PantallaListaProductos
import com.example.cappucareapp.ui.screen.PantallaRegistroProducto
import com.example.cappucareapp.ui.screen.PantallaMovimientos
import com.example.cappucareapp.ui.screen.PantallaRecursoNativo
import com.example.cappucareapp.ui.theme.CappucareTheme
import com.example.cappucareapp.util.VibratorUtil
import com.example.cappucareapp.viewmodel.InventarioViewModel

class MainActivity : ComponentActivity() {

    private val inventarioViewModel: InventarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CappucareTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "pantalla_inicio"
                ) {
                    // 1. Pantalla de Menú Principal
                    composable("pantalla_inicio") {
                        PantallaInicio(
                            onNavegarALista = { navController.navigate("lista_productos") },
                            onNavegarARegistro = { navController.navigate("registro_producto") },
                            onNavegarAMovimientos = { navController.navigate("pantalla_movimientos") },
                            onNavegarACamara = { navController.navigate("recurso_nativo") }
                        )
                    }

                    // 2. Pantalla de Lista de Inventario con Filtro Crítico
                    composable("lista_productos") {
                        val textoBusqueda by inventarioViewModel.textoBusqueda.collectAsState()
                        val soloStockCritico by inventarioViewModel.soloStockCritico.collectAsState()
                        val productosFiltrados by inventarioViewModel.productosFiltrados.collectAsState()

                        PantallaListaProductos(
                            productos = productosFiltrados,
                            textoBusqueda = textoBusqueda,
                            soloStockCritico = soloStockCritico,
                            onCambiarBusqueda = { inventarioViewModel.actualizarBusqueda(it) },
                            onAlternarFiltroCritico = { inventarioViewModel.alternarFiltroCritico() },
                            onNavegarARegistro = { navController.navigate("registro_producto") },
                            onNavegarACamara = { navController.navigate("recurso_nativo") },
                            onVolver = { navController.popBackStack() }
                        )
                    }

                    // 3. Pantalla de Formulario de Registro
                    composable("registro_producto") {
                        val context = LocalContext.current
                        PantallaRegistroProducto(
                            onGuardarProducto = { codigo, nombre, categoria, stock, stockMin, ubicacion ->
                                inventarioViewModel.agregarProducto(codigo, nombre, categoria, stock, stockMin, ubicacion)

                                // Recurso Nativo 2: Vibración al guardar con éxito
                                VibratorUtil.dispararVibracion(context, 250)

                                navController.popBackStack()
                            },
                            onVolver = { navController.popBackStack() }
                        )
                    }

                    // 4. Pantalla de Historial de Movimientos
                    composable("pantalla_movimientos") {
                        val movimientos by inventarioViewModel.listaMovimientos.collectAsState()
                        PantallaMovimientos(
                            movimientos = movimientos,
                            onVolver = { navController.popBackStack() }
                        )
                    }

                    // 5. Pantalla de Cámara (Recurso Nativo)
                    composable("recurso_nativo") {
                        PantallaRecursoNativo(
                            onVolver = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}