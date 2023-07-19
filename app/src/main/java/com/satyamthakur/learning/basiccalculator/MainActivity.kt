package com.satyamthakur.learning.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satyamthakur.learning.basiccalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val bind get() = binding!!

    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}