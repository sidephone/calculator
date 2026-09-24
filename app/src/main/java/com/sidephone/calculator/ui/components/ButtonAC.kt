package com.sidephone.calculator.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R

@Composable
fun ButtonAC(modifier: Modifier = Modifier, onClick: () -> Unit) {
	Button(
		modifier = modifier,
		textResId = R.string.button_ac,
		onClick = onClick,
		background = MaterialTheme.colorScheme.secondary,
		textColor = MaterialTheme.colorScheme.onSecondary,
	)
}
