package com.example.mycurrencyapp.models.symbolsModel

data class SymbolsResult(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val symbols: Map<String, String>? = null,
    val error: String = ""
)
