package com.sidephone.calculator.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun T9Screen(modifier: Modifier, expression: String) {
	Text("T9 layout: $expression", modifier = modifier)
}
