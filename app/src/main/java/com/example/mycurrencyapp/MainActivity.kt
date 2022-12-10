package com.example.mycurrencyapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.example.mycurrencyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragmentContainerView)
        when(navController.currentDestination?.id) {
            R.id.convertCurrencyFragment-> {
                val dialog= AlertDialog.Builder(this)

                dialog.setMessage("Do you want to Exit ?")
                dialog.setNegativeButton("no", DialogInterface.OnClickListener { dialog, which ->

                })
                dialog.setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                }).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}