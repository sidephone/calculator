package com.sidephone.calculator.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun ButtonEquals(modifier: Modifier = Modifier, onClick: () -> Unit) {
	Button(
		modifier = modifier,
		textResId = R.string.button_equals,
		onClick = onClick,
		background = MaterialTheme.colorScheme.primary,
		textColor = MaterialTheme.colorScheme.onPrimary,
		textSize = Dimens.buttonLargeTextSize
	)
}
