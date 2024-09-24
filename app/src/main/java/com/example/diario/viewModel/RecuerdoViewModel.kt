package com.example.diario.viewModel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.diario.data.model.Recuerdo
import java.util.UUID

class RecuerdoViewModel : ViewModel() {
    // Lista mutable de recuerdos que se mantiene en tiempo de ejecución.
    private val _recuerdos = mutableStateListOf<Recuerdo>()
    val recuerdos: List<Recuerdo> = _recuerdos

    // Variable temporal para almacenar la imagen seleccionada
    var imagenSeleccionadaUri: Uri? = null
        private set

    // Variable temporal para almacenar la ubicación seleccionada
    var ubicacionSeleccionada: String? = null
        private set

    // Método para agregar un nuevo recuerdo.
    fun agregarRecuerdo(recuerdo: Recuerdo) {
        _recuerdos.add(recuerdo)
    }

    // Método para actualizar un recuerdo existente.
    fun actualizarRecuerdo(updatedRecuerdo: Recuerdo) {
        _recuerdos.indexOfFirst { it.id == updatedRecuerdo.id }.takeIf { it != -1 }?.let {
            _recuerdos[it] = updatedRecuerdo
        }
    }

    // Método para obtener un recuerdo por ID.
    fun obtenerRecuerdo(id: UUID): Recuerdo? {
        return _recuerdos.find { it.id == id }
    }

    // Método para establecer la URI de la imagen seleccionada.
    fun setImagenSeleccionada(uri: Uri) {
        imagenSeleccionadaUri = uri
    }

    // Método para establecer la ubicación seleccionada.
    fun setUbicacionSeleccionada(ubicacion: String) {
        ubicacionSeleccionada = ubicacion
    }

    // Método para limpiar la imagen seleccionada.
    fun limpiarImagenSeleccionada() {
        imagenSeleccionadaUri = null
    }

    // Método para limpiar la ubicación seleccionada.
    fun limpiarUbicacionSeleccionada() {
        ubicacionSeleccionada = null
    }
}