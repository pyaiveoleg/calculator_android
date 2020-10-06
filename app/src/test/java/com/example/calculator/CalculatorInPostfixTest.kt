package com.example.calculator

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals

class CalculatorInPostfixTest {
    @Test
    fun calculate_emptyString() {
        val calculator = CalculatorInPostfix()
        assertThrows(Exception::class.java) {
            calculator.calculate("")
        }
    }

    @Test
    fun calculate_usualString() {
        val calculator = CalculatorInPostfix()
        assertEquals(1.2857142857142858, calculator.calculate("1 2 + 3 * 7 /"))
    }

    @Test
    fun calculate_incorrectNotation() {
        val calculator = CalculatorInPostfix()
        assertThrows(Exception::class.java) {
            calculator.calculate("23\\45+=-4")
        }
    }

    @Test
    fun calculate_incorrectSymbols() {
        val calculator = CalculatorInPostfix()
        assertThrows(Exception::class.java) {
            calculator.calculate("$345*")
        }
    }

    @Test
    fun calculate_oneNumber() {
        val calculator = CalculatorInPostfix()
        assertEquals(1.0, calculator.calculate("1"))
    }

    @Test
    fun calculate_manyDigitNumbers() {
        val calculator = CalculatorInPostfix()
        assertEquals(579.0, calculator.calculate("123 456 +"))
    }

    @Test
    fun calculate_infixInsteadOfPostfix() {
        val calculator = CalculatorInPostfix()
        assertThrows(Exception::class.java) {
            assertEquals(579.0, calculator.calculate("123 + 456"))
        }
    }

    @Test
    fun calculate_dividingByZero() {
        val calculator = CalculatorInPostfix()
        assertThrows(Exception::class.java) {
            calculator.calculate("1 0 /")
        }
    }

    @Test
    fun calculate_zeroDividingByNumber() {
        val calculator = CalculatorInPostfix()
        assertEquals(0.0, calculator.calculate("0 1 /"))
    }

    @Test
    fun calculate_allOperations() {
        val calculator = CalculatorInPostfix()
        assertEquals(12.0, calculator.calculate("0 1 + 2 * 22 + 2 /"))
    }

    @Test
    fun calculate_withoutGaps() {
        val calculator = CalculatorInPostfix()
        assertEquals(12.0, calculator.calculate("0 1+2*22+2/"))
    }

    @Test
    fun calculate_gapsAreSomewhere() {
        val calculator = CalculatorInPostfix()
        assertEquals(12.0, calculator.calculate("0 1+ 2* 22+2/"))
    }
}
