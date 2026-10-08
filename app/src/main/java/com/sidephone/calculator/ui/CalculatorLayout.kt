package com.sidephone.calculator.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.input.Keypad
import com.sidephone.calculator.ui.panels.PanelTouch
import com.sidephone.calculator.ui.panels.compactQwerty.PanelCompactQwerty
import com.sidephone.calculator.ui.panels.t9.PanelT9

@Composable
fun CalculatorLayout(modifier: Modifier, calculator: Calculator) {
	when (calculator.layout.collectAsState().value) {
		Keypad.Layout.COMPACT_QWERTY -> PanelCompactQwerty(
			modifier = modifier,
			calculator = calculator
		)

		Keypad.Layout.T9,
		Keypad.Layout.T9_NO_DPAD -> PanelT9(
			modifier = modifier,
			calculator = calculator
		)

		else -> PanelTouch(
			modifier = modifier,
			calculator = calculator
		)
	}
}
