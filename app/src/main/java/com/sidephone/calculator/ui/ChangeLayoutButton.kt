package com.sidephone.calculator.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.input.Keypad

@Composable
fun ChangeLayoutButton(modifier: Modifier, calculator: Calculator) {
	val expression = calculator.expression.collectAsState().value
	if (expression.isNotEmpty() && expression != "0") {
		return
	}

	val title = when (calculator.layoutOverride.collectAsState().value) {
		Keypad.Layout.COMPACT_QWERTY -> stringResource(id = R.string.layout_qwerty)
		Keypad.Layout.T9,
		Keypad.Layout.T9_NO_DPAD -> stringResource(id = R.string.layout_t9)
		Keypad.Layout.NONE -> stringResource(id = R.string.layout_none)
		else -> stringResource(id = R.string.layout_auto)
	}

	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		IconButton(
			modifier = Modifier.padding(horizontal = 0.dp),
			onClick = { calculator.nextLayout() }
		) {
			Text("\u2328", style = MaterialTheme.typography.titleMedium)
		}
		Text(title, style = MaterialTheme.typography.labelLarge)
	}
}
