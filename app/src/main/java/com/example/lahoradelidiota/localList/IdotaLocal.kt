package com.example.lahoradelidiota.localList

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class IdiotaLocal(
    val imagenUriString: String?, // Cambio: Almacenar la Uri como una cadena segura
    val numeroDeIdiota: String,
    val nombre: String,
    val nivel: String,
    val site: String,
    val habilidadEspecial: String,
    val descripcion: String
) : Parcelable {
    // Nuevo método para obtener la Uri a partir de la cadena almacenada
    fun getImagenUri(): Uri? {
        return imagenUriString?.let { Uri.parse(Uri.decode(it)) }
    }
}

