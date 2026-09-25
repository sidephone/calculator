package com.sidephone.calculator.ui.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Action
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.ui.components.ExpressionField
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.components.HintGrid

@Composable
fun PanelCompactQwerty(modifier: Modifier, calculator: Calculator) {
	val grid: List<List<Hint?>> = listOf(
		listOf(
			Hint(
				functionLabelResId = R.string.button_period,
				functionLabelIsLarge = true,
				keySymbol = "#",
				onClick = { calculator.onAction(Action.PERIOD) }),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				keySymbol = "\u23CE",
				primaryStyle = true,
				onClick = { calculator.onAction(Action.EQUALS) }),
			Hint(
				keySymbol = "📞",
				functionLabelResId = R.string.button_ac,
				secondaryStyle = true,
				keySlashed = true,
				keyIsEmoji = true,
				onClick = { calculator.onAction(Action.ALL_CLEAR) }),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_add, functionLabelIsLarge = true, keySymbol = "QW", onClick = { calculator.onAction(Action.ADD) }),
			Hint(functionLabelResId = R.string.button_multiply, functionLabelIsLarge = true, keySymbol = "OP", onClick = { calculator.onAction(Action.MULTIPLY) }),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_subtract, functionLabelIsLarge = true, keySymbol = "AS", onClick = { calculator.onAction(Action.SUBTRACT) }),
			Hint(functionLabelResId = R.string.button_divide, functionLabelIsLarge = true, keySymbol = "L", onClick = { calculator.onAction(Action.DIVIDE) }),
		),
	)

	Column(
		modifier = modifier
			.fillMaxSize()
			.background(color = MaterialTheme.colorScheme.background)
	) {
		Row(modifier = Modifier.weight(1f)) { ExpressionField(calculator) }
		Row { HintGrid(grid, modifier = Modifier.fillMaxWidth()) }
	}
}
