package com.sidephone.calculator.ui.panels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.ui.components.ExpressionField
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.components.HintGrid

@Composable
fun PanelCompactQwerty(modifier: Modifier, expression: String) {
	val grid: List<List<Hint?>> = listOf(
		listOf(
			Hint(functionLabelResId = R.string.button_add, functionLabelIsLarge = true, keySymbol = "QW"),
			Hint(functionLabelResId = R.string.button_multiply, functionLabelIsLarge = true, keySymbol = "OP"),
		),

		listOf(
			Hint(functionLabelResId = R.string.button_subtract, functionLabelIsLarge = true, keySymbol = "AS"),
			Hint(functionLabelResId = R.string.button_divide, functionLabelIsLarge = true, keySymbol = "L"),
		),

		listOf(
			Hint(keySymbol = "📞", functionLabelResId = R.string.button_ac, secondaryStyle = true, keySlashed = true, keyIsEmoji = true),
			Hint(functionLabelResId = R.string.button_period, functionLabelIsLarge = true, keySymbol = "#"),
			Hint(functionLabelResId = R.string.button_equals, functionLabelIsLarge = true, keySymbol = "\u23CE", primaryStyle = true),
		),
	)

	Column(modifier = modifier.fillMaxSize()) {
		Row(modifier = Modifier.weight(1f)) { ExpressionField(expression) }
		Row { HintGrid(grid, modifier = Modifier.fillMaxWidth()) }
	}
}
