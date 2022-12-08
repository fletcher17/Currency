package com.example.mycurrencyapp.presentation

import com.example.mycurrencyapp.models.symbolsModel.SymbolsResponse

data class CurrencyDetailsState(
    val isLoading: Boolean? = false,
    val currency: SymbolsResponse? = null
) {

}
