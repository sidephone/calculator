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
			Hint(functionLabelResId = R.string.button_ac, secondaryStyle = true),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_add, functionLabelIsLarge = true),
			Hint(functionLabelResId = R.string.button_1),
			Hint(functionLabelResId = R.string.button_2),
			Hint(functionLabelResId = R.string.button_3),
			Hint(functionLabelResId = R.string.button_multiply, functionLabelIsLarge = true),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_subtract, functionLabelIsLarge = true),
			Hint(functionLabelResId = R.string.button_4),
			Hint(functionLabelResId = R.string.button_5),
			Hint(functionLabelResId = R.string.button_6),
			Hint(functionLabelResId = R.string.button_divide, functionLabelIsLarge = true),
		),

		listOf(
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = R.string.button_7),
			Hint(functionLabelResId = R.string.button_8),
			Hint(functionLabelResId = R.string.button_9),
			Hint(functionLabelResId = R.string.button_backspace),
		),

		listOf(
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = 0),
			Hint(functionLabelResId = R.string.button_0),
			Hint(functionLabelResId = R.string.button_period, functionLabelIsLarge = true),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				primaryStyle = true,
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
