package com.example.lahoradelidiota.others
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class Idiota(val imageUrl: String ,
                  val numeroDeIdiota: String,
                  val nombre: String,
                  val nivel: String,
                  val site: String,
                  val habilidadEspecial: String,
                  val descripcion: String) : Parcelable
