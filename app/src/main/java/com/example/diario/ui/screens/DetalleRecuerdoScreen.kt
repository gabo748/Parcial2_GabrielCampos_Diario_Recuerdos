package com.example.diario.ui.screens


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diario.R
import com.example.diario.data.model.Recuerdo
import com.example.diario.viewModel.RecuerdoViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetalleRecuerdoScreen(
    viewModel: RecuerdoViewModel = viewModel(),
    recuerdoId: UUID?,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Verificar si es un nuevo recuerdo o uno existente
    val esNuevoRecuerdo = recuerdoId == null

    // Crear un nuevo recuerdo si el id es nulo (nuevo recuerdo)
    val recuerdo = if (esNuevoRecuerdo) {
        Recuerdo(titulo = "")
    } else {
        viewModel.obtenerRecuerdo(recuerdoId!!) ?: Recuerdo(titulo = "")
    }

    var titulo by remember { mutableStateOf(recuerdo.titulo) }
    var imagenUri by remember { mutableStateOf<Uri?>(recuerdo.imagenUri?.toUri()) }
    var imagenBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var ubicacion by remember { mutableStateOf(recuerdo.ubicacion) }

    // Cargar la imagen si existe un URI
    LaunchedEffect(imagenUri) {
        imagenUri?.let {
            imagenBitmap = cargarBitmapDesdeUri(context, it)
        }
    }

    // Permiso de almacenamiento
    val permisoAlmacenamiento = rememberPermissionState(
        permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
    )

    // Permiso de cámara
    val permisoCamara = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    // Permisos de ubicación
    val permisosUbicacionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Cliente para obtener la ubicación
    val fusedLocationClient = remember {
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    // Configurar el launcher para abrir la galería
    val seleccionarImagenLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.setImagenSeleccionada(it)
            imagenUri = it
            imagenBitmap = cargarBitmapDesdeUri(context, it)
        }
    }

    // Configurar el launcher para tomar fotos con la cámara
    val tomarFotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            imagenUri?.let {
                imagenBitmap = cargarBitmapDesdeUri(context, it)
            }
        }
    }

    // Archivo temporal para guardar la imagen capturada
    val fotoUri = remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo para el título del recuerdo.
        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título del Recuerdo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Imagen seleccionada
        imagenBitmap?.let { bitmap ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen del Recuerdo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Botones para agregar multimedia y tomar foto
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (permisoAlmacenamiento.status.isGranted) {
                        seleccionarImagenLauncher.launch("image/*")
                    } else {
                        permisoAlmacenamiento.launchPermissionRequest()
                    }
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Agregar Multimedia")
            }

            Button(
                onClick = {
                    if (permisoCamara.status.isGranted) {
                        val fotoFile = crearImagenArchivo(context)
                        fotoFile?.let {
                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.fileprovider",
                                it
                            )
                            fotoUri.value = uri
                            imagenUri = uri
                            tomarFotoLauncher.launch(uri)
                        }
                    } else {
                        permisoCamara.launchPermissionRequest()
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Tomar Foto")
            }
        }

        // Campo para la ubicación del recuerdo
        TextField(
            value = ubicacion ?: "",
            onValueChange = { ubicacion = it },
            label = { Text("Ubicación del Recuerdo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            readOnly = true
        )

        // Botón para agregar ubicación
        Button(
            onClick = {
                if (permisosUbicacionState.allPermissionsGranted) {
                    obtenerUbicacion(context, fusedLocationClient) { latitud, longitud ->
                        ubicacion = "Lat: $latitud, Lon: $longitud"
                    }
                } else {
                    permisosUbicacionState.launchMultiplePermissionRequest()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Agregar Ubicación")
        }

        // Botón para guardar el recuerdo.
        Button(
            onClick = {
                if (esNuevoRecuerdo) {
                    // Si es un nuevo recuerdo, agregarlo
                    viewModel.agregarRecuerdo(
                        Recuerdo(
                            titulo = titulo,
                            imagenUri = imagenUri?.toString(),
                            ubicacion = ubicacion
                        )
                    )
                    // Enviar notificación de nuevo recuerdo
                    enviarNotificacion(
                        context,
                        "Nuevo Recuerdo Agregado",
                        "Se ha agregado el recuerdo \"$titulo\" correctamente."
                    )
                } else {
                    // Si no, actualizar el recuerdo existente
                    viewModel.actualizarRecuerdo(
                        recuerdo.copy(
                            titulo = titulo,
                            imagenUri = imagenUri?.toString(),
                            ubicacion = ubicacion
                        )
                    )
                    // Enviar notificación de actualización de recuerdo
                    enviarNotificacion(
                        context,
                        "Recuerdo Actualizado",
                        "Se ha actualizado el recuerdo \"$titulo\" correctamente."
                    )
                }
                onBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Guardar Recuerdo")
        }
    }
}

// Función para obtener la ubicación del dispositivo
fun obtenerUbicacion(
    context: Context,
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient,
    onLocationObtained: (Double, Double) -> Unit
) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                onLocationObtained(it.latitude, it.longitude)
            } ?: Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
    }
}

// Función para cargar el Bitmap desde la URI seleccionada
fun cargarBitmapDesdeUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            BitmapFactory.decodeStream(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Función para crear un archivo temporal para la imagen
fun crearImagenArchivo(context: Context): File? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            absolutePath
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}


fun enviarNotificacion(context: Context, titulo: String, mensaje: String) {
    val notificationId = System.currentTimeMillis().toInt()
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(context, "canal_recuerdos")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(titulo)
        .setContentText(mensaje)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    notificationManager.notify(notificationId, builder.build())
}