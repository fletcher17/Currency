package com.example.mycurrencyapp.models.convert

data class ConversionResult(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val conversion: ConversionResponse? = null,
    val error: String = ""
)
