package com.sidephone.calculator.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import java.text.DecimalFormatSymbols

@Composable
fun rememberDecimalSeparator(): String {
	val locale = LocalConfiguration.current.locales[0]
	return remember(locale) { DecimalFormatSymbols.getInstance(locale).decimalSeparator.toString() }
}

@Composable
fun ButtonPeriod(modifier: Modifier = Modifier, onClick: () -> Unit) {
	Button(
		modifier = modifier,
		text = rememberDecimalSeparator(),
		onClick = onClick
	)
}
