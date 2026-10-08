package com.sidephone.calculator.calculator

import android.hardware.input.InputManager
import com.sidephone.calculator.Settings
import com.sidephone.calculator.input.CalculatorKeypad
import com.sidephone.calculator.input.Keypad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class Calculator(private val settings: Settings, inputManager: InputManager) {
	private val engine = Engine()
	private val keypad = CalculatorKeypad(inputManager)
	private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	val expression: StateFlow<String> = engine.expression

	private val _layoutOverride = MutableStateFlow(settings.getScreenLayout())
	val layout: StateFlow<Keypad.Layout> = combine(keypad.layout, _layoutOverride) { detected, override ->
		if (override != Keypad.Layout.UNKNOWN) override else detected
	}.stateIn(scope, SharingStarted.Eagerly, keypad.layout.value)


	init {
		keypad.onAction = { action -> engine.onAction(action) }
	}

	fun destroy() { scope.cancel() }
	fun listenForKeypadChange() = keypad.listenForChanges()
	fun onAction(action: Action) = engine.onAction(action)
	fun onKeyDown(keyCode: Int) = keypad.onKeyDown(keyCode)
	fun onKeyUp(keyCode: Int) = keypad.onKeyUp(keyCode)
	fun setLayoutOverride(preferred: Keypad.Layout) {
		settings.setScreenLayout(preferred)
		_layoutOverride.value = preferred
	}
	fun stopListeningForKeypadChange() = keypad.stopListening()
}
