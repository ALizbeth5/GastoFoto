package com.example.gastofoto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gastofoto.domain.model.Gasto
import com.example.gastofoto.data.repository.GastoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ExchangeUiState {
    object Loading : ExchangeUiState
    data class Success(val rates: Map<String, Double>) : ExchangeUiState
    data class Error(val message: String) : ExchangeUiState
}

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {

    private val _exchangeState = MutableStateFlow<ExchangeUiState>(ExchangeUiState.Loading)
    val exchangeState: StateFlow<ExchangeUiState> = _exchangeState.asStateFlow()

    init {
        fetchRates("USD") // Base por defecto, luego se puede cambiar
    }

    fun fetchRates(base: String) {
        viewModelScope.launch {
            _exchangeState.value = ExchangeUiState.Loading
            try {
                val response = repository.getExchangeRates(base)
                if (response.result == "success") {
                    _exchangeState.value = ExchangeUiState.Success(response.rates)
                } else {
                    _exchangeState.value = ExchangeUiState.Error("Error en la API")
                }
            } catch (e: Exception) {
                _exchangeState.value = ExchangeUiState.Error("Sin conexión a internet")
            }
        }
    }

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
