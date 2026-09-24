package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.view.KeyEvent
import com.sidephone.calculator.calculator.Action

class CalculatorKeypad(inputManager: InputManager) : Keypad(inputManager) {
	private val keyActions = mutableMapOf<Int, Action>() // empty = use touchscreen
	internal var onAction: (Action) -> Unit = { action -> onAction(action) }


	init {
		detect()
	}


	override fun onChange() {
		when (layout.value) {
			Layout.COMPACT_QWERTY -> setCompactQwertyLayout()
			Layout.T9 -> setT9Layout()
			else -> keyActions.clear()
		}
	}


	fun onKeyDown(keyCode: Int): Boolean {
		keyActions.getOrDefault(keyCode, null)?.let { action ->
			onAction(action)
			return true
		}

		return false
	}


	fun onKeyUp(keyCode: Int): Boolean {
		return keyActions.containsKey(keyCode)
	}


	private fun setCompactQwertyLayout() {
		keyActions.clear()

		// digits
		keyActions[KeyEvent.KEYCODE_SPACE] = Action.NUM_0
		keyActions[KeyEvent.KEYCODE_E] = Action.NUM_1
		keyActions[KeyEvent.KEYCODE_T] = Action.NUM_2
		keyActions[KeyEvent.KEYCODE_U] = Action.NUM_3
		keyActions[KeyEvent.KEYCODE_D] = Action.NUM_4
		keyActions[KeyEvent.KEYCODE_G] = Action.NUM_5
		keyActions[KeyEvent.KEYCODE_J] = Action.NUM_6
		keyActions[KeyEvent.KEYCODE_C] = Action.NUM_7
		keyActions[KeyEvent.KEYCODE_B] = Action.NUM_8
		keyActions[KeyEvent.KEYCODE_M] = Action.NUM_9
		keyActions[KeyEvent.KEYCODE_SHIFT_LEFT] = Action.PERIOD

		// basic arithmetic
		keyActions[KeyEvent.KEYCODE_O] = Action.ADD
		keyActions[KeyEvent.KEYCODE_Q] = Action.SUBTRACT
		keyActions[KeyEvent.KEYCODE_L] = Action.MULTIPLY
		keyActions[KeyEvent.KEYCODE_A] = Action.DIVIDE

		// main functions
		keyActions[KeyEvent.KEYCODE_ENDCALL] = Action.ALL_CLEAR
		keyActions[KeyEvent.KEYCODE_DEL] = Action.BACKSPACE
		keyActions[KeyEvent.KEYCODE_ENTER] = Action.EQUALS
	}


	private fun setT9Layout() {
		keyActions.clear()

		// digits
		keyActions[KeyEvent.KEYCODE_0] = Action.NUM_0
		keyActions[KeyEvent.KEYCODE_1] = Action.NUM_1
		keyActions[KeyEvent.KEYCODE_2] = Action.NUM_2
		keyActions[KeyEvent.KEYCODE_3] = Action.NUM_3
		keyActions[KeyEvent.KEYCODE_4] = Action.NUM_4
		keyActions[KeyEvent.KEYCODE_5] = Action.NUM_5
		keyActions[KeyEvent.KEYCODE_6] = Action.NUM_6
		keyActions[KeyEvent.KEYCODE_7] = Action.NUM_7
		keyActions[KeyEvent.KEYCODE_8] = Action.NUM_8
		keyActions[KeyEvent.KEYCODE_9] = Action.NUM_9
		keyActions[KeyEvent.KEYCODE_POUND] = Action.PERIOD

		// basic arithmetic
		keyActions[KeyEvent.KEYCODE_DPAD_UP] = Action.ADD
		keyActions[KeyEvent.KEYCODE_DPAD_DOWN] = Action.SUBTRACT
		keyActions[KeyEvent.KEYCODE_DPAD_RIGHT] = Action.MULTIPLY
		keyActions[KeyEvent.KEYCODE_DPAD_LEFT] = Action.DIVIDE

		// main functions
		keyActions[KeyEvent.KEYCODE_ENDCALL] = Action.ALL_CLEAR
		keyActions[KeyEvent.KEYCODE_DEL] = Action.BACKSPACE
		keyActions[KeyEvent.KEYCODE_ENTER] = Action.EQUALS
	}
}
