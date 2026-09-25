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
fun PanelT9(modifier: Modifier, calculator: Calculator) {
	val grid: List<List<Hint?>> = listOf(
		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_add,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.ADD) }),
			Hint(
				keySymbol = "📞",
				functionLabelResId = R.string.button_ac,
				secondaryStyle = true,
				keySlashed = true,
				keyIsEmoji = true,
				onClick = { calculator.onAction(Action.ALL_CLEAR) }),
		),

		listOf(
			Hint(
				functionLabelResId = R.string.button_divide,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.DIVIDE) }),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				primaryStyle = true,
				onClick = { calculator.onAction(Action.EQUALS) }),
			Hint(
				functionLabelResId = R.string.button_multiply,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.MULTIPLY) }),
		),

		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_subtract,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.SUBTRACT) }),
			Hint(
				functionLabelResId = R.string.button_period,
				functionLabelIsLarge = true,
				keySymbol = "#",
				onClick = { calculator.onAction(Action.PERIOD) }),
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
