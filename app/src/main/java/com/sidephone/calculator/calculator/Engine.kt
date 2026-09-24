package com.sidephone.calculator.calculator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class Engine {
	private companion object {
		const val ERROR = "Error"
		val OPERATORS = setOf(Action.ADD, Action.SUBTRACT, Action.MULTIPLY, Action.DIVIDE)
		val OPERATOR_CHARS = OPERATORS.map { it.symbol!![0] }
	}

	private val _expression = MutableStateFlow("")
	val expression: StateFlow<String> = _expression

	private var justEvaluated = false   // last action was '=' (or an error)


	fun onAction(action: Action) {
		when (action) {
			Action.EQUALS -> evaluateExpression()
			Action.ALL_CLEAR -> reset("")
			Action.BACKSPACE -> {
				if (justEvaluated) reset("") else _expression.update { it.dropLast(1) }
			}

			else -> append(action)
		}
	}


	private fun append(action: Action) {
		val symbol = action.symbol ?: return
		val isOperator = action in OPERATORS

		if (justEvaluated) {
			if (action == Action.RIGHT_PAREN) return   // nothing to close after a result
			// Digit, period or '(' starts fresh; an operator continues from the result.
			// (After an error the text is "Error", which is never continued.)
			if (!isOperator || _expression.value == ERROR) _expression.value = ""
			justEvaluated = false
		}

		// Consecutive operators: the newest one replaces the previous one.
		if (isOperator && _expression.value.lastOrNull()?.let { it in OPERATOR_CHARS } == true) {
			_expression.update { it.dropLast(1) + symbol }
		} else {
			_expression.update { it + symbol }
		}
	}


	private fun evaluateExpression() {
		if (justEvaluated) return   // already showing a result or an error

		try {
			val result = ExpressionParser(_expression.value).parse()
			reset(result.stripTrailingZeros().toPlainString(), evaluated = true)
		} catch (_: IncompleteExpressionException) {
			// Incomplete expression: do nothing, let the user keep typing.
		} catch (_: Exception) {   // ArithmeticException, IllegalArgumentException
			reset(ERROR, evaluated = true)
		}
	}


	private fun reset(text: String, evaluated: Boolean = false) {
		_expression.value = text
		justEvaluated = evaluated
	}
}
