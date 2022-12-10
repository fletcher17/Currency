package com.example.mycurrencyapp.domain.symbolUseCase

import android.util.Log
import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.domain.mapper.toListSymbol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSymbolsUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    fun getCurrencySymbols(): Flow<Resource<Map<String, String>>> = flow {

        try {
            emit(Resource.Loading())
            val symbols = repository.getCurrencySymbols()
            emit(Resource.Success(symbols.symbols?.toListSymbol()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
        }
    }
}