package com.sidephone.calculator

import android.hardware.input.InputManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.input.Keypad
import com.sidephone.calculator.ui.panels.PanelCompactQwerty
import com.sidephone.calculator.ui.panels.PanelT9
import com.sidephone.calculator.ui.panels.PanelTouch
import com.sidephone.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
	private lateinit var calculator: Calculator

	override fun onCreate(savedInstanceState: Bundle?) {
		calculator = Calculator(getSystemService(INPUT_SERVICE) as InputManager)

		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			CalculatorTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					val expression = calculator.expression.collectAsState().value
					val layout = calculator.layout.collectAsState().value

					when (layout) {
						Keypad.Layout.COMPACT_QWERTY -> PanelCompactQwerty(
							modifier = Modifier.padding(innerPadding),
							calculator = calculator
						)
						Keypad.Layout.T9 -> PanelT9(
							modifier = Modifier.padding(innerPadding),
							expression = expression
						)
						else -> PanelTouch(
							modifier = Modifier.padding(innerPadding),
							calculator = calculator
						)
					}
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		calculator.listenForKeypadChange()
	}

	override fun onPause() {
		super.onPause()
		calculator.stopListeningForKeypadChange()
	}

	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		return calculator.onKeyDown(keyCode) || super.onKeyDown(keyCode, event)
	}

	override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
		return calculator.onKeyUp(keyCode) || super.onKeyUp(keyCode, event)
	}
}
