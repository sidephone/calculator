package com.sidephone.calculator.ui.panels.t9

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Action
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.ui.components.ExpressionField
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.theme.Dimens


@Composable
fun PanelT9(modifier: Modifier, calculator: Calculator, allowTouch: Boolean) {
	val dpadHints: List<List<Hint?>> = listOf(
		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_divide,
				functionLabelIsLarge = true,
				onClick = if (allowTouch) {{ calculator.onAction(Action.DIVIDE) }} else null
			),
			null,
			null,
		),

		listOf(
			Hint(
				functionLabelResId = R.string.button_add,
				functionLabelIsLarge = true,
				onClick = if (allowTouch) {{ calculator.onAction(Action.ADD) }} else null
			),
			Hint(
				functionLabelResId = R.string.button_equals,
				functionLabelIsLarge = true,
				primaryStyle = true,
				onClick = if (allowTouch) {{ calculator.onAction(Action.EQUALS) }} else null
			),
			Hint(
				functionLabelResId = R.string.button_subtract,
				functionLabelIsLarge = true,
				onClick = if (allowTouch) {{ calculator.onAction(Action.SUBTRACT) }} else null
			),
			null
		),

		listOf(
			null,
			Hint(
				functionLabelResId = R.string.button_multiply,
				functionLabelIsLarge = true,
				onClick = if (allowTouch) {{ calculator.onAction(Action.MULTIPLY) }} else null
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
		ExpressionField(modifier = Modifier.weight(4f), calculator = calculator)
		HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.dividerHorizontal))
		Row(modifier = Modifier.weight(3f)) {
			Box(modifier = Modifier.width(Dimens.hintCellMinWidth))
			HintsDpad(modifier = Modifier.weight(1f), grid = dpadHints)
			VerticalDivider(modifier = Modifier.fillMaxHeight().padding(horizontal = Dimens.dividerVertical))
			HintsT9Side(
				modifier = Modifier.padding(horizontal = Dimens.hintCellT9Padding).width(Dimens.hintCellMinWidth),
				top = Hint(
					functionLabelResId = R.string.button_ac,
					secondaryStyle = true,
					onClick = if (allowTouch) {{ calculator.onAction(Action.ALL_CLEAR) }} else null
				),
				middle = Hint(
					functionLabelResId = R.string.button_backspace,
					onClick = if (allowTouch) {{ calculator.onAction(Action.BACKSPACE) }} else null
				),
				bottom = Hint(
					functionLabelResId = R.string.button_period,
					functionLabelIsLarge = true,
					onClick = if (allowTouch) {{ calculator.onAction(Action.PERIOD) }} else null
				)
			)
		}
	}
}
