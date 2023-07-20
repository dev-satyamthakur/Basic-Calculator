package com.satyamthakur.learning.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.satyamthakur.learning.basiccalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception

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

        bind.btnAc.setOnClickListener {
            bind.inputTv.text = ""
            bind.outputTv.text = ""
            stateError = false
            lastDot = false
            lastNumeric = false
            bind.outputTv.visibility = View.GONE
        }

        bind.btnEqual.setOnClickListener {
            onEqual()
            bind.inputTv.text = bind.outputTv.text.toString().drop(1)
        }

        bind.btnBackspace.setOnClickListener {
            bind.inputTv.text = bind.inputTv.text.toString().dropLast(1)
            try {
                val lastChar = bind.inputTv.text.toString().last()
                if (lastChar.isDigit()) {
                    onEqual()
                }
            }
            catch (e: Exception) {
                bind.outputTv.text = ""
                bind.outputTv.visibility = View.GONE
                Log.e("MYAPP", e.toString())
            }
        }

    }

    fun onDigitClick(view: View) {
        if (stateError) {
            bind.inputTv.text = (view as Button).text
            stateError = false
        }
        else {
            bind.inputTv.append((view as Button).text)
        }

        lastNumeric = true
        onEqual()
    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric) {
            bind.inputTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = bind.inputTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                bind.outputTv.isVisible = true
                bind.outputTv.text = result.toString()
            }
            catch (e: ArithmeticException) {
                Log.e("MYAPP", "Evaluation error $e")
                bind.outputTv.text = "Error!"
                stateError = true
                lastNumeric = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}