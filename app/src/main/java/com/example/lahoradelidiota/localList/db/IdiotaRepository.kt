package com.example.lahoradelidiota.localList.db

import androidx.lifecycle.LiveData
import com.example.lahoradelidiota.localList.IdiotaLocal
import com.example.lahoradelidiota.localList.IdiotaLocalDao

class IdiotaRepository(private val idiotaLocalDao: IdiotaLocalDao) {
    val allIdiotas: LiveData<List<IdiotaLocal>> = idiotaLocalDao.getAllIdiotas()

    suspend fun insert(idiotaLocal: IdiotaLocal) {
        idiotaLocalDao.insert(idiotaLocal)
    }
    fun getIdiotaById(id: Long): LiveData<IdiotaLocal> {
        return idiotaLocalDao.getIdiotaById(id)
    }
    suspend fun delete(idiotaLocal: IdiotaLocal) {
        idiotaLocalDao.delete(idiotaLocal)
    }
}
