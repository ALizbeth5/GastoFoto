package com.example.gastofoto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gastofoto.data.Gasto
import com.example.gastofoto.data.GastoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {

    val todosLosGastos: StateFlow<List<Gasto>> = repository.allGastos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.insert(gasto)
        }
    }

    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.delete(gasto)
        }
    }

    // Factory para instanciar el ViewModel con el repositorio
    class Factory(private val repository: GastoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GastoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GastoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
