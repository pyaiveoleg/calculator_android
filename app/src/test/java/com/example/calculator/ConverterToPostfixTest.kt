package com.example.calculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows

class ConverterToPostfixTest {
    @Test
    fun convert_emptyString() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("", converterToPostfix.convert(""))
    }

    @Test
    fun convert_oneOperation() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 2 +", converterToPostfix.convert("1 + 2"))
    }

    @Test
    fun convert_twoDigitNumbers() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("11 23 +", converterToPostfix.convert("11 + 23"))
    }

    @Test
    fun convert_complicatedExpression() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("11 23 8 * + 7 +", converterToPostfix.convert("11 + 23 * 8 + 7"))
    }

    @Test
    fun convert_incorrectCharacters() {
        val converterToPostfix = ConverterToPostfix()
        assertThrows(Exception::class.java) {
            converterToPostfix.convert("\$4568*")
        }
    }

    @Test
    fun convert_dividingByZero() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 0 /", converterToPostfix.convert("1 / 0"))
    }

    @Test
    fun convert_withoutGaps() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 123 / 65 * 2 +", converterToPostfix.convert("1/123*65+2"))
    }

    @Test
    fun convert_gapsAreSomewhere() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 123 / 65 * 2 +", converterToPostfix.convert("1/123* 65 +2"))
    }

    @Test
    fun convert_withPairOfBrackets() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 2 4 + *", converterToPostfix.convert("1 * (2 + 4)"))
    }

    @Test
    fun convert_withManyBrackets() {
        val converterToPostfix = ConverterToPostfix()
        assertEquals("1 2 4 + 1 3 7 / + * +", converterToPostfix.convert("1 + (2 + 4) * (1 + (3 / 7))"))
    }

    @Test
    fun convert_alreadyInPostfix() {
        val converterToPostfix = ConverterToPostfix()
        assertThrows(Exception::class.java) {
            converterToPostfix.convert("1 2 4 + 1 3 7 / + * +")
        }
    }

    @Test
    fun convert_twoOperatorsSequentially() {
        val converterToPostfix = ConverterToPostfix()
        assertThrows(Exception::class.java) {
            converterToPostfix.convert("1 + + 1")
        }
    }
}
