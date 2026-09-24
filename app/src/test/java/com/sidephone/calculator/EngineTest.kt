package com.sidephone.calculator

import com.sidephone.calculator.calculator.Actions
import com.sidephone.calculator.calculator.Engine
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for [Engine], driven through a tiny key notation so scenarios stay readable:
 *
 *   0-9 . + - * / ( )   the matching key
 *   =                   EQUALS
 *   C                   ALL_CLEAR
 *   <                   BACKSPACE
 *
 * `evaluate("2+3=")` runs the keys on a fresh engine and returns the displayed text.
 * For multistep scenarios, create an engine and call `engine.type(...)` repeatedly.
 */
class EngineTest {

	private val error = "Error"   // must match Engine's error text

	private val keyMap: Map<Char, Actions> =
		('0'..'9').associateWith { Actions.valueOf("NUM_$it") } + mapOf(
			'.' to Actions.PERIOD,
			'+' to Actions.ADD,
			'-' to Actions.SUBTRACT,
			'*' to Actions.MULTIPLY,
			'/' to Actions.DIVIDE,
			'(' to Actions.LEFT_PAREN,
			')' to Actions.RIGHT_PAREN,
			'=' to Actions.EQUALS,
			'C' to Actions.ALL_CLEAR,
			'<' to Actions.BACKSPACE,
		)

	/** Presses the keys in order and returns the text shown afterwards. */
	private fun Engine.type(keys: String): String {
		for (k in keys) onAction(requireNotNull(keyMap[k]) { "Unknown key '$k'" })
		return expression.value
	}

	private fun evaluate(keys: String): String = Engine().type(keys)

	// ---- typing ----------------------------------------------------------

	@Test
	fun startsEmpty() {
		assertEquals("", Engine().expression.value)
	}

	@Test
	fun typedKeysBuildTheExpression() {
		assertEquals("12+3.5*(4-1)/2", evaluate("12+3.5*(4-1)/2"))
	}

	// ---- backspace and clear --------------------------------------------

	@Test
	fun backspaceRemovesLastCharacter() {
		assertEquals("12", evaluate("123<"))
		assertEquals("5", evaluate("5+<"))
		assertEquals("(2", evaluate("(2+<"))
	}

	@Test
	fun backspaceOnEmptyDoesNothing() {
		assertEquals("", evaluate("<"))
		assertEquals("", evaluate("5<<<"))
	}

	@Test
	fun allClearEmptiesTheExpression() {
		assertEquals("", evaluate("12+3C"))
	}

	@Test
	fun typingWorksAfterAllClear() {
		assertEquals("4", evaluate("12+3C4"))
	}

	// ---- consecutive operators ------------------------------------------

	@Test
	fun newestOperatorReplacesThePreviousOne() {
		assertEquals("5*", evaluate("5+*"))
		assertEquals("5/3", evaluate("5+*/3"))
		assertEquals("5/", evaluate("5+-*/"))
	}

	@Test
	fun minusAfterAnotherOperatorReplacesIt() {
		// Documents the current behavior: "5*-3" cannot be typed from the keypad.
		assertEquals("5-3", evaluate("5*-3"))
		assertEquals("2", evaluate("5*-3="))
	}

	@Test
	fun leadingMinusIsKeptSoNegativeNumbersCanBeTyped() {
		assertEquals("-", evaluate("-"))
		assertEquals("-2", evaluate("-5+3="))
	}

	@Test
	fun operatorIsStillReplacedAfterIncompleteEquals() {
		val engine = Engine()
		assertEquals("2+", engine.type("2+="))
		assertEquals("2*", engine.type("*"))
	}

	// ---- evaluation ------------------------------------------------------

	@Test
	fun evaluatesBasicOperations() {
		assertEquals("5", evaluate("2+3="))
		assertEquals("1", evaluate("3-2="))
		assertEquals("12", evaluate("3*4="))
		assertEquals("2.5", evaluate("10/4="))
	}

	@Test
	fun respectsPrecedenceAndParentheses() {
		assertEquals("14", evaluate("2+3*4="))
		assertEquals("21", evaluate("(1+2)*(3+4)="))
		assertEquals("5", evaluate("((1+2)*3)-4="))
	}

	@Test
	fun resultIsFormattedAsPlainNumber() {
		assertEquals("0.3", evaluate("0.1+0.2="))
		assertEquals("5", evaluate("2.5*2="))          // no trailing ".0"
		assertEquals("100", evaluate("10*10="))        // no "1E+2"
		assertEquals("1000000", evaluate("1000*1000="))
		assertEquals("0", evaluate("5-5="))
		assertEquals("0", evaluate("0.0*5="))
		assertEquals("-2", evaluate("3-5="))
		assertEquals("1", evaluate(".5+.5="))
		assertEquals("0.3333333333333333", evaluate("1/3="))
	}

	// ---- incomplete expressions: "=" does nothing ------------------------

	@Test
	fun equalsOnEmptyDoesNothing() {
		assertEquals("", evaluate("="))
	}

	@Test
	fun equalsOnTrailingOperatorDoesNothing() {
		assertEquals("2+", evaluate("2+="))
		assertEquals("3*(4+", evaluate("3*(4+="))
	}

	@Test
	fun equalsOnUnclosedParenthesisDoesNothing() {
		assertEquals("(", evaluate("(="))
		assertEquals("(2+3", evaluate("(2+3="))
	}

	@Test
	fun canKeepTypingAfterIncompleteEquals() {
		val engine = Engine()
		assertEquals("2+", engine.type("2+="))
		assertEquals("5", engine.type("3="))     // digit is appended, not treated as a fresh start
	}

	@Test
	fun canCloseParenthesisAfterIncompleteEquals() {
		val engine = Engine()
		assertEquals("(2+3", engine.type("(2+3="))
		assertEquals("5", engine.type(")="))
	}

	// ---- invalid expressions: "Error" ------------------------------------

	@Test
	fun divisionByZeroShowsError() {
		assertEquals(error, evaluate("1/0="))
		assertEquals(error, evaluate("0/0="))
		assertEquals(error, evaluate("5/(3-3)="))
	}

	@Test
	fun malformedExpressionsShowError() {
		assertEquals(error, evaluate("2+3)="))
		assertEquals(error, evaluate("()="))
		assertEquals(error, evaluate("1.2.3="))
		assertEquals(error, evaluate(".="))
		assertEquals(error, evaluate("*2="))
		assertEquals(error, evaluate("(2)(3)="))
	}

	// ---- after a result ---------------------------------------------------

	@Test
	fun digitAfterResultStartsNewExpression() {
		val engine = Engine()
		assertEquals("5", engine.type("2+3="))
		assertEquals("7", engine.type("7"))
		assertEquals("14", engine.type("*2="))
	}

	@Test
	fun periodAfterResultStartsNewExpression() {
		assertEquals(".", evaluate("2+3=."))
	}

	@Test
	fun leftParenthesisAfterResultStartsNewExpression() {
		assertEquals("(", evaluate("2+3=("))
	}

	@Test
	fun operatorAfterResultContinuesFromIt() {
		assertEquals("5*", evaluate("2+3=*"))
		assertEquals("20", evaluate("2+3=*4="))
		assertEquals("10", evaluate("2+3=*(1+1)="))
	}

	@Test
	fun operatorCanBeReplacedAfterResult() {
		assertEquals("5*", evaluate("2+3=+*"))
	}

	@Test
	fun resultsCanBeChained() {
		assertEquals("15", evaluate("2+3=*4=-5="))
		assertEquals("-1", evaluate("3-5=+1="))    // continues from a negative result
	}

	@Test
	fun closingParenthesisAfterResultIsIgnored() {
		val engine = Engine()
		assertEquals("5", engine.type("2+3=)"))
		assertEquals("7", engine.type("7"))        // still counts as "just evaluated"
	}

	@Test
	fun repeatedEqualsKeepsTheResult() {
		assertEquals("5", evaluate("2+3=="))
		assertEquals("5", evaluate("2+3==="))
	}

	@Test
	fun backspaceAfterResultClearsEverything() {
		val engine = Engine()
		assertEquals("", engine.type("2+3=<"))
		assertEquals("4", engine.type("4"))
	}

	@Test
	fun allClearAfterResultClearsEverything() {
		assertEquals("", evaluate("2+3=C"))
		assertEquals("4", evaluate("2+3=C4"))
	}

	// ---- after an error ---------------------------------------------------

	@Test
	fun digitAfterErrorStartsNewExpression() {
		val engine = Engine()
		assertEquals(error, engine.type("1/0="))
		assertEquals("3", engine.type("3"))        // "Error" is never concatenated with input
		assertEquals("5", engine.type("+2="))
	}

	@Test
	fun leftParenthesisAfterErrorStartsNewExpression() {
		assertEquals("(", evaluate("1/0=("))
	}

	@Test
	fun backspaceAfterErrorClearsEverything() {
		assertEquals("", evaluate("1/0=<"))
	}

	@Test
	fun allClearAfterErrorClearsEverything() {
		assertEquals("", evaluate("1/0=C"))
	}

	@Test
	fun equalsAfterErrorKeepsTheError() {
		assertEquals(error, evaluate("1/0=="))
	}

	@Test
	fun closingParenthesisAfterErrorIsIgnored() {
		assertEquals(error, evaluate("1/0=)"))
	}
}
