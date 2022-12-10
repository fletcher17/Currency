package com.example.mycurrencyapp.features

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mycurrencyapp.R
import com.example.mycurrencyapp.common.Resource
import com.example.mycurrencyapp.databinding.FragmentConvertCurrencyBinding
import com.example.mycurrencyapp.models.symbolsModel.SymbolsResult
import com.example.mycurrencyapp.viewModels.CurrencyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertCurrencyFragment : Fragment() {
    private var _binding: FragmentConvertCurrencyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels()

    val countryList = mutableListOf<String>()
    var lastValue : Boolean? = null

    var fromAmount = ""
    var toAmount = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConvertCurrencyBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        viewModel.getSymbols()

        binding.detailsButton.setOnClickListener {
            findNavController().navigate(R.id.detailsFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.symbolCurrency.collectLatest {
                val result: Resource<SymbolsResult> = it ?: return@collectLatest
                Log.d("convert listed", "$countryList")

//                if(result.isLoading) {
//                    binding.progressBar.visibility = View.VISIBLE
//                } else if (result.success) {
//                    binding.progressBar.visibility = View.GONE
//                    result.symbols?.forEach { symbol ->
//                        countryList.add(symbol.key)
//                    }
//                    Log.d("convert list", "$countryList")
//                    Log.d("convert", "${result.symbols}")
//                } else {
//                    Log.d("error coming", "${response.error}")
//                    binding.progressBar.visibility = View.GONE
//                    Snackbar.make(binding.root, response.error, Snackbar.LENGTH_LONG)
//                        .setAction("Retry") {
//                            viewModel.getSymbols()
//                        }.show()
//                }
                when(result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.symbols?.forEach { symbol ->
                            countryList.add(symbol.key)
                        }
                        Log.d("convert list", "$countryList")
                        Log.d("convert", "${result}")
                    }
                    is Resource.Error -> {
                        Log.d("error coming", "${result.message}")
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG)
                            .setAction("Retry") {
                                viewModel.getSymbols()
                            }.show()

                    }
                }


            }
        }

        observer()

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_layout, countryList)
        binding.fromCountryAutoTextView.setAdapter(arrayAdapter)

        binding.fromEditText.addTextChangedListener(fromTextViewWatcher)
        binding.toEditText.addTextChangedListener(toTextViewWatcher)

        binding.toCountryAutoTextView.setAdapter(arrayAdapter)
//        binding.toCountryAutoTextView.addTextChangedListener(toTextWatcher)


        return view
    }

    private val fromTextViewWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(textChar: CharSequence?, start: Int, count: Int, after: Int) {
//            binding.toEditText.text = textChar as Editable?
            lastValue = true
            fromAmount = textChar.toString()
            viewModel.getRate(textChar.toString(), binding.toCountryAutoTextView.text.toString(), binding.fromCountryAutoTextView.text.toString())
            Log.d("result2", textChar.toString())

        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private val toTextViewWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(textChar: CharSequence?, start: Int, count: Int, after: Int) {
//            binding.toEditText.text = textChar as Editable?
            lastValue = false
            toAmount = textChar.toString()
            viewModel.getRate(textChar.toString(), binding.toCountryAutoTextView.text.toString(), binding.fromCountryAutoTextView.text.toString())

        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private val toTextWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            lastValue?.let {  it ->
                if(it){
                    viewModel.getRate(fromAmount, binding.toCountryAutoTextView.text.toString(), binding.fromCountryAutoTextView.text.toString())

                }else {
                    viewModel.getRate(toAmount, binding.toCountryAutoTextView.text.toString(), binding.fromCountryAutoTextView.text.toString())
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun observer(){
        lifecycleScope.launchWhenStarted {
            viewModel.conversionRate.collectLatest {
                val result = it
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        binding.toEditText.setText(result.data?.conversion?.result.toString())
                        binding.rateTv.text = getString(R.string.the_conversion_rate_is, result.data?.conversion?.info?.rate.toString())
                        Log.d("result1", "$result")
                    }
                    is Resource.Error -> {
                        Log.d("result11", "${result.data?.error}")
                        result.data?.let { it1 -> Snackbar.make(binding.root, it1.error, Snackbar.LENGTH_LONG).show() }
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}