package com.sidephone.calculator.calculator

import android.hardware.input.InputManager
import com.sidephone.calculator.input.CalculatorKeypad
import kotlinx.coroutines.flow.StateFlow

class Calculator(inputManager: InputManager) {
	private val engine = Engine()
	private val keypad = CalculatorKeypad(inputManager)
	val expression: StateFlow<String> = engine.expression

	init {
		keypad.onAction = { action -> engine.onAction(action) }
		keypad.detect()
	}

	fun listenForKeypadChange() = keypad.listenForChanges()
	fun onKeyDown(keyCode: Int) = keypad.onKeyDown(keyCode)
	fun onKeyUp(keyCode: Int) = keypad.onKeyUp(keyCode)
	fun stopListeningForKeypadChange() = keypad.stopListening()
}
