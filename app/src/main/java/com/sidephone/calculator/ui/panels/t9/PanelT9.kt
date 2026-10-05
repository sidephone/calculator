package com.sidephone.calculator.ui.panels.t9

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
fun PanelT9(modifier: Modifier, calculator: Calculator) {
	val dpadHints: List<List<Hint?>> = listOf(
		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_divide,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.DIVIDE) }
			),
			null,
			null,
		),

		listOf(
			Hint(
				functionLabelResId = R.string.button_add,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.ADD) }
			),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				primaryStyle = true,
				onClick = { calculator.onAction(Action.EQUALS) }
			),
			Hint(
				functionLabelResId = R.string.button_subtract,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.SUBTRACT) }
			),
			null
		),

		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_multiply,
				functionLabelIsLarge = true,
				onClick = { calculator.onAction(Action.MULTIPLY) }
			),
			null,
			null,
		)
	)

	Column(
		modifier = modifier
			.fillMaxSize()
			.background(color = MaterialTheme.colorScheme.background)
	) {
		Row(modifier = Modifier.weight(4f)) { ExpressionField(calculator) }
		Row(modifier = Modifier.weight(3f)) {
			Box(modifier = Modifier.width(Dimens.hintCellMinWidth))
			HintsDpad(modifier = Modifier.weight(1f), grid = dpadHints)
			HintsT9Side(
				modifier = Modifier.width(Dimens.hintCellMinWidth),
				top = Hint(
					functionLabelResId = R.string.button_ac,
					secondaryStyle = true,
					onClick = { calculator.onAction(Action.ALL_CLEAR) }
				),
				middle = Hint(
					functionLabelResId = R.string.button_backspace,
					onClick = { calculator.onAction(Action.BACKSPACE) }
				),
				bottom = Hint(
					functionLabelResId = R.string.button_period,
					functionLabelIsLarge = true,
					onClick = { calculator.onAction(Action.PERIOD) }
				)
			)
		}
	}
}
