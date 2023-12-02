package com.example.lahoradelidiota.localList

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class LocalStorage private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "local_storage_prefs"
        private const val KEY_LOCAL_ITEMS = "local_items"

        @Volatile
        private var INSTANCE: LocalStorage? = null

        fun getInstance(context: Context): LocalStorage {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalStorage(context).also { INSTANCE = it }
            }
        }
    }

    // En el método saveLocalItems
    fun saveLocalItems(localItems: List<IdiotaLocal>) {
        val json = Gson().toJson(localItems)
        sharedPreferences.edit().putString(KEY_LOCAL_ITEMS, json).apply()
    }

    // En el método getLocalItems
    fun getLocalItems(): List<IdiotaLocal> {
        val json = sharedPreferences.getString(KEY_LOCAL_ITEMS, "")
        val type = object : TypeToken<List<IdiotaLocal>>() {}.type
        val localItems = Gson().fromJson<List<IdiotaLocal>>(json, type) ?: emptyList()

        // Reconstruir la Uri para cada IdiotaLocal
        return localItems.map { idiotaLocal ->
            idiotaLocal.copy(imagenUriString = idiotaLocal.imagenUriString)
        }
    }
}
