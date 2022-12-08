package com.example.mycurrencyapp.features

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mycurrencyapp.R
import com.example.mycurrencyapp.databinding.FragmentConvertCurrencyBinding
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResult
import com.example.mycurrencyapp.viewModels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {
    private var _binding: FragmentConvertCurrencyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels()

    val countryList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConvertCurrencyBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        viewModel.getSymbols()

        lifecycleScope.launchWhenStarted {
            viewModel.symbolCurrency.collectLatest {
                val result: SymbolsResult = it ?: return@collectLatest


                result.symbols?.forEach { symbol ->
                    countryList.add(symbol.key)
                }
                Log.d("convert list", "$countryList")
                Log.d("convert", "${result.symbols}")
            }
        }




        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}