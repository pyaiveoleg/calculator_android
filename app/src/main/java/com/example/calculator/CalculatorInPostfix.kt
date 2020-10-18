package com.example.calculator

import java.lang.Exception
import kotlin.math.pow

class CalculatorInPostfix {
    private val charParser = CharParser()

    private fun binaryOperation(
        currentSymbol: Char,
        firstOperand: Double,
        secondOperand: Double
    ): Double {
        var resultingValue = 0.0
        when (currentSymbol) {
            '+' -> resultingValue = firstOperand + secondOperand
            '-' -> resultingValue = firstOperand - secondOperand
            '*' -> resultingValue = firstOperand * secondOperand
            '/' -> {
                if (secondOperand == 0.0) {
                    throw Exception("DividingByZero")
                }
                resultingValue = firstOperand / secondOperand
            }
        }
        return resultingValue
    }

    private fun calculateBinaryOperation(stack: MutableList<Double>, currentSymbol: Char): Boolean {
        if (stack.isEmpty()) {
            return false
        }
        val secondOperand = stack.removeAt(stack.lastIndex)
        if (stack.isEmpty()) {
            return false
        }
        val firstOperand = stack.removeAt(stack.lastIndex)
        if (secondOperand == 0.0) {
            return false
        }
        val result = binaryOperation(currentSymbol, firstOperand, secondOperand)
        stack.add(result)
        return true
    }

    fun calculate(expression: String): Double {
        val stack = mutableListOf<Double>()
        var currentNumber = 0.0
        var wasDigit = false
        var decimalPlaceAfterDot = 1
        var currentNumberIsNatural = true

        for ((index, currentSymbol) in expression.withIndex()) {
            when {
                charParser.isOperator(currentSymbol) -> {
                    if (wasDigit) {
                        stack.add(currentNumber)
                        currentNumber = 0.0
                        wasDigit = false
                    }
                    val error = !calculateBinaryOperation(stack, currentSymbol)
                    if (error) {
                        throw Exception("IncorrectBinaryOperation")
                    }
                }
                charParser.isDigit(currentSymbol) -> {
                    wasDigit = true
                    if (currentNumberIsNatural) {
                        currentNumber = 10 * currentNumber + currentSymbol.toInt() - '0'.toInt()
                    } else {
                        currentNumber += (currentSymbol.toInt() - '0'.toInt()) * 10.0.pow(-decimalPlaceAfterDot)
                        decimalPlaceAfterDot += 1
                    }
                    if (index == expression.length - 1) {
                        stack.add(currentNumber)
                    }
                }
                currentSymbol == '.' -> {
                    if (!wasDigit) {
                        throw Exception("Incorrect dot.")
                    }
                    currentNumberIsNatural = false
                }
                currentSymbol == ' ' -> {
                    if (wasDigit) {
                        stack.add(currentNumber)
                        currentNumber = 0.0
                        wasDigit = false
                        decimalPlaceAfterDot = 1
                        currentNumberIsNatural = true
                    }
                }
                else -> throw Exception("UnexpectedSymbol")
            }
        }
        val resultingValue = stack.removeAt(stack.lastIndex)
        if (stack.isNotEmpty()) {
            throw Exception("Incorrect expression")
        }
        return resultingValue
    }
}
