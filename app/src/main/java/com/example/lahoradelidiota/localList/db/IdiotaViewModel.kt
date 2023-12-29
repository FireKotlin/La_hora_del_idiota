package com.example.lahoradelidiota.localList.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lahoradelidiota.localList.IdiotaLocal
import kotlinx.coroutines.launch

class IdiotaViewModel(private val repository: IdiotaRepository) : ViewModel() {
    val allIdiotas: LiveData<List<IdiotaLocal>> = repository.allIdiotas

    fun insert(idiotaLocal: IdiotaLocal) = viewModelScope.launch {
        repository.insert(idiotaLocal)
    }
    fun getIdiotaById(id: Long): LiveData<IdiotaLocal> {
        return repository.getIdiotaById(id)
    }
    fun deleteIdiota(idiotaLocal: IdiotaLocal) {
        viewModelScope.launch {
            repository.delete(idiotaLocal)
        }
    }
}
