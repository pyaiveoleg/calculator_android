package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expressionEnterField = findViewById<TextView>(R.id.expressionEnterField)
        val buttonsList = arrayOf(R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.zero,
            R.id.plus, R.id.minus, R.id.multiply, R.id.divide, R.id.openBracket, R.id.closingBracket)
        for (buttonId in buttonsList) {
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener {
                when (buttonId) {
                    R.id.one -> expressionEnterField.append("1")
                    R.id.two -> expressionEnterField.append("2")
                    R.id.three -> expressionEnterField.append("3")
                    R.id.four -> expressionEnterField.append("4")
                    R.id.five -> expressionEnterField.append("5")
                    R.id.six -> expressionEnterField.append("6")
                    R.id.seven -> expressionEnterField.append("7")
                    R.id.eight -> expressionEnterField.append("8")
                    R.id.nine -> expressionEnterField.append("9")
                    R.id.zero -> expressionEnterField.append("0")
                    R.id.plus -> expressionEnterField.append("+")
                    R.id.minus -> expressionEnterField.append("-")
                    R.id.multiply -> expressionEnterField.append("*")
                    R.id.divide -> expressionEnterField.append("/")
                    R.id.openBracket -> expressionEnterField.append("(")
                    R.id.closingBracket -> expressionEnterField.append(")")
                }
            }
        }
        findViewById<Button>(R.id.backspace).setOnClickListener {
            val length = expressionEnterField.length()
            if (length > 0) {
                expressionEnterField.text = expressionEnterField.text.toString().substring(0 until length - 1)
            }
        }
        findViewById<Button>(R.id.calculate).setOnClickListener {
            try {
                val expression = ConverterToPostfix().convert(expressionEnterField.text.toString())
                val result = CalculatorInPostfix().calculate(expression)
                findViewById<TextView>(R.id.resultField).text = getString(R.string.result, expressionEnterField.text, result.toString())
            } catch (e: Exception) {
                findViewById<TextView>(R.id.resultField).text = getString(R.string.error)
            }
        }
    }
}
