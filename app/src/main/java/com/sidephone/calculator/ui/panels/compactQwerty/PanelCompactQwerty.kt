package com.sidephone.calculator.ui.panels.compactQwerty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Action
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.ui.components.ExpressionField
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun PanelCompactQwerty(modifier: Modifier, calculator: Calculator) {
	val grid: List<List<Hint?>> = listOf(
		listOf(
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = R.string.button_ac, secondaryStyle = true, onClick = { calculator.onAction(Action.ALL_CLEAR) }),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_add, functionLabelIsLarge = true, onClick = { calculator.onAction(Action.ADD) }),
			Hint(functionLabelResId = R.string.button_1, onClick = { calculator.onAction(Action.NUM_1) }),
			Hint(functionLabelResId = R.string.button_2, onClick = { calculator.onAction(Action.NUM_2) }),
			Hint(functionLabelResId = R.string.button_3, onClick = { calculator.onAction(Action.NUM_3) }),
			Hint(functionLabelResId = R.string.button_multiply, functionLabelIsLarge = true, onClick = { calculator.onAction(Action.MULTIPLY) }),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_subtract, functionLabelIsLarge = true, onClick = { calculator.onAction(Action.SUBTRACT) }),
			Hint(functionLabelResId = R.string.button_4, onClick = { calculator.onAction(Action.NUM_4) }),
			Hint(functionLabelResId = R.string.button_5, onClick = { calculator.onAction(Action.NUM_5) }),
			Hint(functionLabelResId = R.string.button_6, onClick = { calculator.onAction(Action.NUM_6) }),
			Hint(functionLabelResId = R.string.button_divide, functionLabelIsLarge = true, onClick = { calculator.onAction(Action.DIVIDE) }),
		),

		listOf(
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = R.string.button_7, onClick = { calculator.onAction(Action.NUM_7) }),
			Hint(functionLabelResId = R.string.button_8, onClick = { calculator.onAction(Action.NUM_8) }),
			Hint(functionLabelResId = R.string.button_9, onClick = { calculator.onAction(Action.NUM_9) }),
			Hint(functionLabelResId = R.string.button_backspace, onClick = { calculator.onAction(Action.BACKSPACE) }),
		),

		listOf(
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = R.string.button_0, onClick = { calculator.onAction(Action.NUM_0) }),
			Hint(functionLabelResId = R.string.button_period, functionLabelIsLarge = true, onClick = { calculator.onAction(Action.PERIOD) }),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				primaryStyle = true,
				onClick = { calculator.onAction(Action.EQUALS) }
			),
		),
	)

	Column(
		modifier = modifier
			.fillMaxSize()
			.background(color = MaterialTheme.colorScheme.background)
	) {
		ExpressionField(modifier = Modifier.weight(1f), calculator = calculator)
		HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.dividerHorizontal))
		HintsCompactQwerty(grid, modifier = Modifier.fillMaxWidth())
	}
}
