package com.sidephone.calculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.ui.components.ExpressionField

@Composable
fun T9Screen(modifier: Modifier, expression: String) {
	Column(
		modifier = modifier.background(color = MaterialTheme.colorScheme.background)
	) {
		ExpressionField(expression)
	}
}
