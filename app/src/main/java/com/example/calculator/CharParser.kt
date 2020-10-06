package com.example.calculator

class CharParser {
    fun isOperator(symbol: Char): Boolean {
        return (symbol == '+') || (symbol == '-') || (symbol == '*') || (symbol == '/')
    }

    fun isDigit(symbol: Char): Boolean {
        return (symbol >= '0') && (symbol <= '9')
    }
}
