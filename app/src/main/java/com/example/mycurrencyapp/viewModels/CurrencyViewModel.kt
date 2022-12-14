package com.example.mycurrencyapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.domain.conversionUseCase.GetConversionRateUseCase
import com.example.mycurrencyapp.domain.symbolUseCase.GetSymbolsUseCase
import com.example.mycurrencyapp.models.convert.ConversionResponse
import com.example.mycurrencyapp.models.convert.ConversionResult
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
    val getSymbolsUseCase: GetSymbolsUseCase,
    val getConversionRate: GetConversionRateUseCase
): ViewModel() {

    private val _symbolCurrency = MutableSharedFlow<Resource<SymbolsResult>>()
    val symbolCurrency = _symbolCurrency.asSharedFlow()

    private val _conversionRate = MutableSharedFlow<Resource<ConversionResult>>()
    val conversionRate = _conversionRate.asSharedFlow()



    fun getSymbols() {

        getSymbolsUseCase.getCurrencySymbols().onEach { result ->

            when (result) {
                is Resource.Success -> {

                    _symbolCurrency.emit(Resource.Success(SymbolsResult(symbols = result.data)))
                }
                is Resource.Error -> {

                    _symbolCurrency.emit(Resource.Error(result.message, SymbolsResult(error = result.message!!)))
                }
                is Resource.Loading -> {

                    _symbolCurrency.emit(Resource.Loading(SymbolsResult(isLoading = true)))
                }
            }


        }.launchIn(viewModelScope)
    }

    fun getRate(amount: String, to: String, from:String) {
        getConversionRate(amount, to, from).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _conversionRate.emit(Resource.Success(ConversionResult(conversion = result.data)))
                }
                is Resource.Error -> {
                    _conversionRate.emit(Resource.Error(result.message))
                }
                is Resource.Loading -> {
                    _conversionRate.emit(Resource.Loading(ConversionResult(isLoading = true)))
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