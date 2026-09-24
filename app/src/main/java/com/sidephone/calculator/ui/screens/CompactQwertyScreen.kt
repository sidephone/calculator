package com.sidephone.calculator.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CompactQwertyScreen(modifier: Modifier, expression: String) {
	Text("Compact QWERTY layout: $expression", modifier = modifier)
}
