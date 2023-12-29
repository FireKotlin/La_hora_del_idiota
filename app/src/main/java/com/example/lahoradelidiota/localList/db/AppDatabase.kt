package com.example.lahoradelidiota.localList.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lahoradelidiota.localList.IdiotaLocal
import com.example.lahoradelidiota.localList.IdiotaLocalDao

@Database(entities = [IdiotaLocal::class], version = 1)
@TypeConverters(UriTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): IdiotaLocalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            // Si la instancia no es nula, la retorna,
            // si es nula, crea la base de datos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "idiota_database"
                ).build()
                INSTANCE = instance
                // Retorna la instancia
                instance
            }
        }
    }
}

