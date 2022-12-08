package com.example.mycurrencyapp.repository

import com.example.mycurrencyapp.api.CurrencyApi
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.domain.mapper.toListSymbol
import com.example.mycurrencyapp.models.symbolsModel.Symbols
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {

    //    suspend fun getCurrencySymbols(): Map<String, String> {
//        return api.getSymbols().symbols?.toListSymbol() ?: mapOf()
//    }
    override suspend fun getCurrencySymbols(): SymbolsResponse {
        return api.getSymbols()
    }


}