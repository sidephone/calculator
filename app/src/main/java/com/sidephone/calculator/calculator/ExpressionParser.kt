package com.sidephone.calculator.calculator

import java.math.BigDecimal
import java.math.MathContext

/** Thrown when the input ends before the expression is complete, e.g. "2+", "3*(4+". */
class IncompleteExpressionException : Exception()

class ExpressionParser(private val src: String) {
	private var pos = 0


	fun parse(): BigDecimal {
		val result = parseExpression()
		if (pos != src.length) throw IllegalArgumentException("Unexpected '${src[pos]}'")
		return result
	}


	// expression := term (('+' | '-') term)*
	private fun parseExpression(): BigDecimal {
		var value = parseTerm()
		while (pos < src.length && (src[pos] == '+' || src[pos] == '-')) {
			val op = src[pos++]
			val rhs = parseTerm()
			value = if (op == '+') value + rhs else value - rhs
		}
		return value
	}


	// factor := ('+' | '-') factor | '(' expression ')' | number
	private fun parseFactor(): BigDecimal {
		if (pos >= src.length) throw IncompleteExpressionException()

		return when (src[pos]) {
			'+', '-' -> {
				val negative = src[pos++] == '-'
				val v = parseFactor()
				if (negative) v.negate() else v
			}

			'(' -> {
				pos++
				val v = parseExpression()
				if (pos >= src.length) throw IncompleteExpressionException()  // missing ')'
				if (src[pos] != ')') throw IllegalArgumentException("Expected ')'")
				pos++
				v
			}

			else -> parseNumber()
		}
	}


	private fun parseNumber(): BigDecimal {
		val start = pos
		while (pos < src.length && (src[pos].isDigit() || src[pos] == '.')) pos++
		if (start == pos) throw IllegalArgumentException("Number expected")
		return src.substring(start, pos).toBigDecimalOrNull() ?: throw IllegalArgumentException("Bad number")
	}


	// term := factor (('*' | '/') factor)*
	private fun parseTerm(): BigDecimal {
		var value = parseFactor()
		while (pos < src.length && (src[pos] == '*' || src[pos] == '/')) {
			val op = src[pos++]
			val rhs = parseFactor()
			value = if (op == '*') value * rhs else value.divide(rhs, MathContext.DECIMAL64)
		}
		return value
	}
}
