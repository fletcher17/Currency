package com.example.mycurrencyapp.domain.conversionUseCase

import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.domain.CurrencyRepository
import com.example.mycurrencyapp.models.convert.ConversionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetConversionRateUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(amount: String, to: String, from: String): Flow<Resource<ConversionResponse>> = flow {
        try {
            emit(Resource.Loading())
            val conversionRate = repository.convertCurrency(amount,to,from)
            emit(Resource.Success(conversionRate))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "an unexpected error occurred"))
        }catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
        }
    }
}