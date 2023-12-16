package com.example.lahoradelidiota.localList

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class IdiotaLocal(
    val imagenUri: Uri?,
    val numeroDeIdiota: String,
    val nombre: String,
    val nivel: String,
    val site: String,
    val habilidadEspecial: String,
    val descripcion: String
) : Parcelable

