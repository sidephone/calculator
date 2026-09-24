package com.sidephone.calculator

import com.sidephone.calculator.calculator.ExpressionParser
import com.sidephone.calculator.calculator.IncompleteExpressionException
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.Test

class ExpressionParserTest {

	// ---- helpers ---------------------------------------------------------

	private fun eval(expr: String): BigDecimal = ExpressionParser(expr).parse()

	/** Compares numerically, so "2" equals "2.0" (BigDecimal.equals cares about scale). */
	private fun assertEval(expected: String, expr: String) {
		val actual = eval(expr)
		assertEquals(
			0, BigDecimal(expected).compareTo(actual),
			"'$expr' expected $expected but was ${actual.toPlainString()}"
		)
	}

	private fun assertIncomplete(expr: String) {
		assertFailsWith<IncompleteExpressionException>("'$expr' should be incomplete") { eval(expr) }
	}

	private fun assertInvalid(expr: String) {
		assertFailsWith<IllegalArgumentException>("'$expr' should be invalid") { eval(expr) }
	}

	// ---- basic arithmetic ------------------------------------------------

	@Test
	fun singleNumber() {
		assertEval("5", "5")
		assertEval("42", "42")
		assertEval("3.14", "3.14")
		assertEval("7", "007")
	}

	@Test
	fun basicOperations() {
		assertEval("5", "2+3")
		assertEval("1", "3-2")
		assertEval("12", "3*4")
		assertEval("2.5", "10/4")
	}

	@Test
	fun decimalShorthand() {
		assertEval("0.5", ".5")
		assertEval("5", "5.")
	}

	// ---- precedence and associativity -----------------------------------

	@Test
	fun multiplicationBindsTighterThanAddition() {
		assertEval("14", "2+3*4")
		assertEval("2", "8-2*3")
		assertEval("4", "2+6/3")
		assertEval("26", "2*3+4*5")
	}

	@Test
	fun operatorsAreLeftAssociative() {
		assertEval("3", "10-4-3")      // (10-4)-3, not 10-(4-3)
		assertEval("5", "100/10/2")    // (100/10)/2, not 100/(10/2)
		assertEval("24", "2*3*4")
	}

	// ---- unary signs -----------------------------------------------------

	@Test
	fun unaryMinusAndPlus() {
		assertEval("-2", "-2")
		assertEval("5", "+5")
		assertEval("2", "-2+4")
		assertEval("-15", "5*-3")
		assertEval("-2", "8/-4")
		assertEval("8", "5--3")
		assertEval("3", "--3")
		assertEval("-3", "-+3")
	}

	// ---- parentheses -----------------------------------------------------

	@Test
	fun parenthesesOverridePrecedence() {
		assertEval("14", "2*(3+4)")
		assertEval("20", "(2+3)*4")
		assertEval("21", "(1+2)*(3+4)")
		assertEval("1", "10-(4+5)")
	}

	@Test
	fun nestedParentheses() {
		assertEval("2", "((2))")
		assertEval("5", "((1+2)*3)-4")
		assertEval("14", "(2*(3+(4/2)))+4")
	}

	@Test
	fun unaryOperatorsWithParentheses() {
		assertEval("-5", "-(2+3)")
		assertEval("-4", "2*-(1+1)")
		assertEval("2", "-(-(2))")
		assertEval("5", "+(2+3)")
	}

	// ---- decimal precision -----------------------------------------------

	@Test
	fun decimalArithmeticIsExact() {
		assertEval("0.3", "0.1+0.2")
		assertEval("0.3", "3*0.1")
		assertEval("3.75", "1.5+2.25")
		assertEval("2", "0.5*4")
	}

	@Test
	fun nonTerminatingDivisionIsRounded() {
		// MathContext.DECIMAL64 gives 16 significant digits
		assertEval("0.3333333333333333", "1/3")
	}

	@Test
	fun largeNumbersDoNotOverflow() {
		val big = "99999999999999999999"
		val expected = BigDecimal(big).pow(2)
		assertEquals(0, expected.compareTo(eval("$big*$big")))
	}

	// ---- incomplete expressions (user is still typing) -------------------

	@Test
	fun incompleteExpressions() {
		assertIncomplete("")
		assertIncomplete("2+")
		assertIncomplete("2-")
		assertIncomplete("2*")
		assertIncomplete("2/")
		assertIncomplete("-")
		assertIncomplete("2*-")
		assertIncomplete("(")
		assertIncomplete("2+(")
		assertIncomplete("(2+3")
		assertIncomplete("3*(4+")
		assertIncomplete("((2+3)*4")
	}

	// ---- invalid expressions (genuinely wrong) ---------------------------

	@Test
	fun unbalancedClosingParenthesis() {
		assertInvalid("2+3)")
		assertInvalid(")")
		assertInvalid("(2+3))")
	}

	@Test
	fun emptyParentheses() {
		assertInvalid("()")
		assertInvalid("2*()")
	}

	@Test
	fun malformedNumbers() {
		assertInvalid("1.2.3")
		assertInvalid(".")
		assertInvalid("2+.")
	}

	@Test
	fun misplacedOperators() {
		assertInvalid("*2")
		assertInvalid("/2")
		assertInvalid("2**3")
		assertInvalid("2*/3")
		assertInvalid("(*2)")
	}

	@Test
	fun implicitMultiplicationIsNotSupported() {
		assertInvalid("2(3)")
		assertInvalid("(2)(3)")
		assertInvalid("(2+3)4")
	}

	@Test
	fun unexpectedCharacters() {
		assertInvalid("abc")
		assertInvalid("2+x")
		assertInvalid("2 + 3")   // the calculator never produces whitespace
	}

	// ---- arithmetic errors -----------------------------------------------

	@Test
	fun divisionByZero() {
		assertFailsWith<ArithmeticException> { eval("1/0") }
		assertFailsWith<ArithmeticException> { eval("0/0") }
		assertFailsWith<ArithmeticException> { eval("5/(3-3)") }
	}
}
