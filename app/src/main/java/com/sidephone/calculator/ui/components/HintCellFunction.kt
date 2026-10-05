package com.sidephone.calculator.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun HintCellFunction(hint: Hint, textColor: Color) {
	Text(
		text = if (hint.functionLabelResId != 0) stringResource(hint.functionLabelResId) else "",
		style = if (hint.functionLabelIsLarge) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
		color = textColor,
		textAlign = TextAlign.Center,
		maxLines = 1,
		overflow = TextOverflow.Clip
	)
}
