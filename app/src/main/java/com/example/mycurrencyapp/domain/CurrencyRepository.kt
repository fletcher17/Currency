package com.example.mycurrencyapp.domain

import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.models.convert.ConversionResponse
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResponse
import dagger.Provides


interface CurrencyRepository {

    suspend fun getCurrencySymbols(): SymbolsResponse

    suspend fun convertCurrency(amount: String, to: String, from: String): ConversionResponse
}