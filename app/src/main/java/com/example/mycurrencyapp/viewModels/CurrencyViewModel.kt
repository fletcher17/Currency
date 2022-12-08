package com.example.mycurrencyapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.domain.symbolUseCase.GetSymbolsUseCase
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResponse
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResult
import com.example.mycurrencyapp.repository.CurrencyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    val getSymbolsUseCase: GetSymbolsUseCase
): ViewModel() {

    private val _symbolCurrency = MutableSharedFlow<SymbolsResult>()
    val symbolCurrency = _symbolCurrency.asSharedFlow()



    fun getSymbols() {
        Log.d("symbols", "Ghyto")
        getSymbolsUseCase.getCurrencySymbols().onEach { result ->
            Log.d("symbols", "Ghyto1")
            when (result) {
                is Resource.Success -> {
                    Log.d("symbols", "Ghyto2")
                    _symbolCurrency.emit(SymbolsResult(symbols = result.data))
                }
                is Resource.Error -> {
                    Log.d("symbols", "Ghyto3")
                    _symbolCurrency.emit(SymbolsResult(error = result.message!!))
                }
                is Resource.Loading -> {
                    Log.d("symbols", "Ghyto4")
                    _symbolCurrency.emit(SymbolsResult(isLoading = true))
                }
            }


        }.launchIn(viewModelScope)
    }
//    fun getSymbol() {
//        viewModelScope.launch {
//            val symbols = currencyRepository.getCurrencySymbols()
//            _symbolCurrency.value = symbols
//        }
//    }

}