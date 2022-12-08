package com.example.mycurrencyapp.api

import com.example.mycurrencyapp.common.ApiConstants
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {

//    @Headers("apikey: ${ApiConstants.API_KEY}")
    @GET("symbols")
    suspend fun getSymbols(): SymbolsResponse
}