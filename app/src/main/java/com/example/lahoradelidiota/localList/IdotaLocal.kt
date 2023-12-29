package com.example.lahoradelidiota.localList

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
@Entity(tableName = "idiota_local_table")
data class IdiotaLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imagenUri: String,
    val numeroDeIdiota: String,
    val nombre: String,
    val nivel: String,
    val site: String,
    val habilidadEspecial: String,
    val descripcion: String
) : Parcelable
