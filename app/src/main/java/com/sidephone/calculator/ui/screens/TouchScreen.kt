package com.sidephone.calculator.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TouchScreen(modifier: Modifier, expression: String) {
	Text("Touchscreen layout: $expression", modifier = modifier)
}
