package com.example.diario.data.model

import java.util.*

data class Recuerdo(
    val id: UUID = UUID.randomUUID(),
    var titulo: String,
    var fechaCreacion: Date = Date(),
    var imagenUri: String? = null,
    var ubicacion: String? = null
)
