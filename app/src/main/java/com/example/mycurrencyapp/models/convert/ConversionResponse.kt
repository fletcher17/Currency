package com.example.mycurrencyapp.models.convert

data class ConversionResponse(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)