package com.example.diario.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diario.data.model.Recuerdo
import com.example.diario.ui.screens.cargarBitmapDesdeUri

@Composable
fun RecuerdoCard(
    recuerdo: Recuerdo,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp), // Esquinas redondeadas
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() } // Acción al hacer clic
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface) // Fondo de la tarjeta
                .padding(16.dp)
        ) {
            // Imagen o ícono del recuerdo
            if (recuerdo.imagenUri != null) {
                val imageBitmap = cargarBitmapDesdeUri(LocalContext.current, Uri.parse(recuerdo.imagenUri))
                imageBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Imagen del Recuerdo",
                        modifier = Modifier
                            .size(64.dp) // Tamaño de la imagen
                            .clip(RoundedCornerShape(8.dp)) // Esquinas redondeadas en la imagen
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = "Icono de Recuerdo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del recuerdo
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                // Título del recuerdo
                Text(
                    text = recuerdo.titulo,
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Fecha de creación del recuerdo
                Text(
                    text = recuerdo.fechaCreacion.toString(), // Asume que `fechaCreacion` es una propiedad del modelo `Recuerdo`
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            // Flecha para indicar más detalles
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ver Detalle",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}