package com.example.calculator

import java.lang.Exception

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
        var currentNumber = 0
        var wasDigit = false
        for ((index, currentSymbol) in expression.withIndex()) {
            when {
                charParser.isOperator(currentSymbol) -> {
                    if (wasDigit) {
                        stack.add(currentNumber.toDouble())
                        currentNumber = 0
                        wasDigit = false
                    }
                    val error = !calculateBinaryOperation(stack, currentSymbol)
                    if (error) {
                        throw Exception("IncorrectBinaryOperation")
                    }
                }
                charParser.isDigit(currentSymbol) -> {
                    wasDigit = true
                    currentNumber = 10 * currentNumber + currentSymbol.toInt() - '0'.toInt()
                    if (index == expression.length - 1) {
                        stack.add(currentNumber.toDouble())
                    }
                }
                currentSymbol == ' ' -> {
                    if (wasDigit) {
                        stack.add(currentNumber.toDouble())
                        currentNumber = 0
                        wasDigit = false
                    }
                }
                else -> throw Exception("UnexpectedSymbol")
            }
        }
        val resultingValue = stack.removeAt(stack.lastIndex)
        if (!stack.isEmpty()) {
            throw Exception("Incorrect expression")
        }
        return resultingValue
    }
}
