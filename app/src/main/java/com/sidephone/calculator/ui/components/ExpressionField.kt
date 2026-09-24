package com.sidephone.calculator.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun ExpressionField(expression: String) {
	Text(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = Dimens.expressionPadding, bottom = Dimens.expressionPaddingBottom, start = Dimens.expressionPadding, end = Dimens.expressionPadding),
		color = MaterialTheme.colorScheme.onBackground,
		style = MaterialTheme.typography.displaySmall,
		text = expression.ifEmpty { "0" },
		textAlign = TextAlign.End,
	)
}
