package com.example.lahoradelidiota.localList

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface IdiotaLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idiotaLocal: IdiotaLocal)

    @Query("SELECT * FROM idiota_local_table")
    fun getAllIdiotas(): LiveData<List<IdiotaLocal>>

    @Query("SELECT * FROM idiota_local_table WHERE id = :id")
    fun getIdiotaById(id: Long): LiveData<IdiotaLocal>

    @Delete
    suspend fun delete(idiotaLocal: IdiotaLocal)
}

