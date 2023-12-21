package com.example.lahoradelidiota.localList

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class IdiotaLocal(
    val imagenUri: String?, // Ahora se almacena la URL de la imagen en lugar de Uri
    val numeroDeIdiota: String,
    val nombre: String,
    val nivel: String,
    val site: String,
    val habilidadEspecial: String,
    val descripcion: String
) : Parcelable
