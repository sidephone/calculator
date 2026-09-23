package com.sidephone.calculator

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sidephone.calculator.input.Keypad
import com.sidephone.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
	private lateinit var keypad: Keypad

	override fun onCreate(savedInstanceState: Bundle?) {
		keypad = Keypad(getSystemService(INPUT_SERVICE) as android.hardware.input.InputManager)
		keypad.detect()

		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			CalculatorTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					val keypadType = when (keypad.current.collectAsState().value) {
						Keypad.TYPE.COMPACT_QWERTY -> "Compact QWERTY"
						Keypad.TYPE.GAMEPAD -> "Gamepad"
						Keypad.TYPE.T9 -> "T9"
						else -> "None"
					}

					Text(
						text = "Keypad Type: $keypadType",
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		keypad.listenForChanges()
	}

	override fun onPause() {
		super.onPause()
		keypad.stopListening()
	}

	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		Log.d("MainActivity", "key pressed: $keyCode")
		if (keyCode == KeyEvent.KEYCODE_ENDCALL) {
			return true
		}

		return super.onKeyDown(keyCode, event)
	}

	override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
		Log.d("MainActivity", "key released: $keyCode")
		if (keyCode == KeyEvent.KEYCODE_ENDCALL) {
			return true
		}
		return super.onKeyUp(keyCode, event)
	}
}
