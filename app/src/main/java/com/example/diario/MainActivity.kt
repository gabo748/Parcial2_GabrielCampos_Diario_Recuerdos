package com.example.diario

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diario.ui.screens.DetalleRecuerdoScreen
import com.example.diario.ui.screens.HomeScreen
import com.example.diario.viewModel.RecuerdoViewModel
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            // Crea el ViewModel a nivel de actividad
            val viewModel: RecuerdoViewModel = viewModel()

            crearCanalNotificacion(this)

            SolicitarPermisoNotificacion()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        viewModel = viewModel, // Pasa el mismo ViewModel a HomeScreen
                        onRecuerdoClick = { recuerdo ->
                            navController.navigate("detalle/${recuerdo.id}") // Navegación con `recuerdoId`
                        },
                        onAddRecuerdoClick = {
                            navController.navigate("detalle") // Navegación sin `recuerdoId`
                        }
                    )
                }
                composable(
                    route = "detalle/{recuerdoId}",
                    arguments = listOf(
                        navArgument("recuerdoId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) { backStackEntry ->
                    val recuerdoId = backStackEntry.arguments?.getString("recuerdoId")
                    DetalleRecuerdoScreen(
                        viewModel = viewModel, // Pasa el mismo ViewModel a DetalleRecuerdoScreen
                        recuerdoId = recuerdoId?.let { UUID.fromString(it) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(route = "detalle") {
                    DetalleRecuerdoScreen(
                        viewModel = viewModel, // Pasa el mismo ViewModel a DetalleRecuerdoScreen
                        recuerdoId = null,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

fun crearCanalNotificacion(context: Context) {
    val nombre = "Canal de Recuerdos"
    val descripcion = "Notificaciones de nuevos recuerdos agregados"
    val importancia = NotificationManager.IMPORTANCE_DEFAULT
    val canal = NotificationChannel("canal_recuerdos", nombre, importancia).apply {
        description = descripcion
    }
    // Registrar el canal con el sistema
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(canal)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SolicitarPermisoNotificacion() {
    val permisoNotificacion = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(Unit) {
        if (!permisoNotificacion.status.isGranted) {
            permisoNotificacion.launchPermissionRequest()
        }
    }
}