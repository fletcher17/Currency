package com.example.mycurrencyapp.domain.mapper

import com.example.mycurrencyapp.models.symbolsModel.Symbols

fun Symbols.toListSymbol(): Map<String, String> {
    return mapOf(
        "AED" to AED,
        "AFN" to AFN,
        "ALL" to ALL,
        "AMD" to AMD,
        "ANG" to ANG,
        "AOA" to AOA,
        "EGP" to EGP,
        "EUR" to EUR,
        "JPY" to JPY,
        "USD" to USD
    )
}