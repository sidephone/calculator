package com.sidephone.calculator.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun ButtonLargeText(
	modifier: Modifier = Modifier,
	textResId: Int,
	onClick: () -> Unit,
	textSize: TextUnit = Dimens.buttonLargeTextSize
) {
	Button(
		modifier = modifier,
		textResId = textResId,
		onClick = onClick,
		textSize = textSize
	)
}
