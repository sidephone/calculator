package com.sidephone.calculator.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.input.Keypad
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun ExpressionField(calculator: Calculator) {
	val expression = calculator.expression.collectAsState().value
	val output = if (expression.isEmpty()) "0"
	else if (expression == "Error") stringResource(R.string.error)
	else expression

	val fontSize = determineFontSize(output, calculator.layout.collectAsState().value)

	Text(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = Dimens.expressionPadding, bottom = Dimens.expressionPaddingBottom, start = Dimens.expressionPadding, end = Dimens.expressionPadding),
		color = MaterialTheme.colorScheme.onBackground,
		style = MaterialTheme.typography.displaySmall,
		fontSize = fontSize,
		lineHeight = fontSize * Dimens.expressionLineSizeMultiplier,
		text = output,
		textAlign = TextAlign.End,
	)
}


fun determineFontSize(expression: String, layout: Keypad.Layout): TextUnit {
	val fontSizes: Map<Int, TextUnit> = when (layout) {
		Keypad.Layout.T9, Keypad.Layout.T9_NO_DPAD -> Dimens.expressionFontSizeT9
		Keypad.Layout.COMPACT_QWERTY -> Dimens.expressionFontSizeCompactQwerty
		else -> Dimens.expressionFontSizeTouchscreen
	}

	for (lengthThreshold in fontSizes.keys.sorted()) {
		if (expression.length <= lengthThreshold) {
			return fontSizes[lengthThreshold] ?: Dimens.expressionFontSizeDefault
		}
	}

	return fontSizes[Int.MAX_VALUE] ?: Dimens.expressionFontSizeDefault
}
