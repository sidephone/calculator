package com.sidephone.calculator.calculator

import android.hardware.input.InputManager
import com.sidephone.calculator.input.CalculatorKeypad
import com.sidephone.calculator.input.Keypad
import kotlinx.coroutines.flow.StateFlow

class Calculator(inputManager: InputManager) {
	private val engine = Engine()
	private val keypad = CalculatorKeypad(inputManager)
	val expression: StateFlow<String> = engine.expression
	val layout: StateFlow<Keypad.Layout> = keypad.layout

	init {
		keypad.onAction = { action -> engine.onAction(action) }
	}

	fun listenForKeypadChange() = keypad.listenForChanges()
	fun onAction(action: Action) = engine.onAction(action)
	fun onKeyDown(keyCode: Int) = keypad.onKeyDown(keyCode)
	fun onKeyUp(keyCode: Int) = keypad.onKeyUp(keyCode)
	fun stopListeningForKeypadChange() = keypad.stopListening()
}
