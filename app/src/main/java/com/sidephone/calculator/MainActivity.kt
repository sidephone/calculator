package com.sidephone.calculator

import android.hardware.input.InputManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.ui.CalculatorLayout
import com.sidephone.calculator.ui.ChangeLayoutButton
import com.sidephone.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
	private lateinit var calculator: Calculator

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		calculator = Calculator(
			Settings(this),
			getSystemService(INPUT_SERVICE) as InputManager
		)

		enableEdgeToEdge()
		setContent {
			CalculatorTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(color = MaterialTheme.colorScheme.background)
							.padding(innerPadding)
					) {
						ChangeLayoutButton(modifier = Modifier.align(Alignment.TopStart), calculator = calculator)
						CalculatorLayout(modifier = Modifier.fillMaxSize(), calculator = calculator)
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

	override fun onDestroy() {
		super.onDestroy()
		calculator.destroy()
	}
}
