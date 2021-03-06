package com.example.calculator

import java.lang.Exception
import kotlin.math.pow

class ConverterToPostfix {
    private val charParser = CharParser()
    private val resultingArray = mutableListOf<String>()

    fun convert(expression: String): String {
        val stack = mutableListOf<String>()
        var currentNumber = 0.0
        var wasDigitBefore = false
        var wasOperatorBefore = true // expression must start with a digit
        var decimalPlaceAfterDot = 1
        var currentNumberIsNatural = true

        expression.forEachIndexed { index, symbol ->
            when {
                charParser.isDigit(symbol) -> {
                    if (!wasOperatorBefore) {
                        throw Exception("Invalid expression")
                    }
                    wasDigitBefore = true
                    if (currentNumberIsNatural) {
                        currentNumber = 10 * currentNumber + symbol.toInt() - '0'.toInt()
                    } else {
                        currentNumber += (symbol.toInt() - '0'.toInt()) * 10.0.pow(-decimalPlaceAfterDot)
                        decimalPlaceAfterDot += 1
                    }

                    if (index == expression.length - 1) {
                        resultingArray.add(currentNumber.toString())
                    }
                }
                symbol == ' ' -> return@forEachIndexed
                symbol == '.' -> {
                    if (!wasDigitBefore) {
                        throw Exception("Incorrect dot.")
                    }
                    currentNumberIsNatural = false
                }
                else -> {
                    if (wasDigitBefore) {
                        resultingArray.add(currentNumber.toString())
                        wasOperatorBefore = false
                        currentNumber = 0.0
                        decimalPlaceAfterDot = 1
                        wasDigitBefore = false
                        currentNumberIsNatural = true
                    }

                    when {
                        charParser.isOperator(symbol) -> {
                            parseOperator(wasOperatorBefore, symbol, stack)
                            wasOperatorBefore = true
                        }
                        symbol == '(' -> stack.add(symbol.toString())
                        symbol == ')' -> parseClosingBracket(stack)
                        else -> throw Exception("UnexpectedSymbol")
                    }
                }
            }
        }
        addRemainingOperators(stack)
        return resultingArray.joinToString(" ")
    }

    private fun getPriority(symbol: Char): Int {
        if (symbol == '+' || symbol == '-') {
            return 1
        }
        if (symbol == '*' || symbol == '/') {
            return 2
        }
        return 0
    }

    private fun parseOperator(
        wasOperatorBefore: Boolean,
        currentSymbol: Char,
        stack: MutableList<String>
    ): Boolean {
        if (wasOperatorBefore) {
            throw Exception("Invalid expression")
        }
        if (stack.isNotEmpty()) {
            var frontValue = stack.last()
            while (stack.isNotEmpty() && getPriority(frontValue.toCharArray()[0]) >= getPriority(
                    currentSymbol
                )
            ) {
                resultingArray.add(stack.removeAt(stack.lastIndex))
                if (stack.isNotEmpty()) {
                    frontValue = stack.last()
                }
            }
        }

        stack.add(currentSymbol.toString())
        return true // wasOperatorBefore = true
    }

    private fun addRemainingOperators(stack: MutableList<String>): Boolean {
        while (stack.isNotEmpty()) {
            val frontValue = stack.last()
            if (frontValue == "(") {
                throw Exception("Missing closing bracket")
            }
            resultingArray.add(frontValue)
            stack.removeAt(stack.lastIndex)
        }
        return true
    }

    private fun parseClosingBracket(stack: MutableList<String>) {
        var frontValue = stack.last()
        while (frontValue != "(") {
            resultingArray.add(stack.removeAt(stack.lastIndex))
            if (stack.size == 0) {
                throw Exception("Invalid expression")
            }
            frontValue = stack.last()
        }
        stack.removeAt(stack.lastIndex)
    }
}
