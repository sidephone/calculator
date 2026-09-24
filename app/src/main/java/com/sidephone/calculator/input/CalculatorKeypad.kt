package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.view.KeyEvent
import com.sidephone.calculator.calculator.Actions

class CalculatorKeypad(inputManager: InputManager) : Keypad(inputManager) {
	private val keyActions = mutableMapOf<Int, Actions>() // empty = use touchscreen
	internal var onAction: (Actions) -> Unit = { action -> onAction(action) }


	init {
		detect()
	}


	override fun onChange() {
		when (current.value) {
			TYPE.COMPACT_QWERTY -> setCompactQwertyLayout()
			TYPE.T9 -> setT9Layout()
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
		keyActions[KeyEvent.KEYCODE_SPACE] = Actions.NUM_0
		keyActions[KeyEvent.KEYCODE_E] = Actions.NUM_1
		keyActions[KeyEvent.KEYCODE_T] = Actions.NUM_2
		keyActions[KeyEvent.KEYCODE_U] = Actions.NUM_3
		keyActions[KeyEvent.KEYCODE_D] = Actions.NUM_4
		keyActions[KeyEvent.KEYCODE_G] = Actions.NUM_5
		keyActions[KeyEvent.KEYCODE_J] = Actions.NUM_6
		keyActions[KeyEvent.KEYCODE_C] = Actions.NUM_7
		keyActions[KeyEvent.KEYCODE_B] = Actions.NUM_8
		keyActions[KeyEvent.KEYCODE_M] = Actions.NUM_9
		keyActions[KeyEvent.KEYCODE_SHIFT_LEFT] = Actions.PERIOD

		// basic arithmetic
		keyActions[KeyEvent.KEYCODE_O] = Actions.ADD
		keyActions[KeyEvent.KEYCODE_Q] = Actions.SUBTRACT
		keyActions[KeyEvent.KEYCODE_L] = Actions.MULTIPLY
		keyActions[KeyEvent.KEYCODE_A] = Actions.DIVIDE

		// main functions
		keyActions[KeyEvent.KEYCODE_ENDCALL] = Actions.ALL_CLEAR
		keyActions[KeyEvent.KEYCODE_DEL] = Actions.BACKSPACE
		keyActions[KeyEvent.KEYCODE_ENTER] = Actions.EQUALS
	}


	private fun setT9Layout() {
		keyActions.clear()

		// digits
		keyActions[KeyEvent.KEYCODE_0] = Actions.NUM_0
		keyActions[KeyEvent.KEYCODE_1] = Actions.NUM_1
		keyActions[KeyEvent.KEYCODE_2] = Actions.NUM_2
		keyActions[KeyEvent.KEYCODE_3] = Actions.NUM_3
		keyActions[KeyEvent.KEYCODE_4] = Actions.NUM_4
		keyActions[KeyEvent.KEYCODE_5] = Actions.NUM_5
		keyActions[KeyEvent.KEYCODE_6] = Actions.NUM_6
		keyActions[KeyEvent.KEYCODE_7] = Actions.NUM_7
		keyActions[KeyEvent.KEYCODE_8] = Actions.NUM_8
		keyActions[KeyEvent.KEYCODE_9] = Actions.NUM_9
		keyActions[KeyEvent.KEYCODE_POUND] = Actions.PERIOD

		// basic arithmetic
		keyActions[KeyEvent.KEYCODE_DPAD_UP] = Actions.ADD
		keyActions[KeyEvent.KEYCODE_DPAD_DOWN] = Actions.SUBTRACT
		keyActions[KeyEvent.KEYCODE_DPAD_RIGHT] = Actions.MULTIPLY
		keyActions[KeyEvent.KEYCODE_DPAD_LEFT] = Actions.DIVIDE

		// main functions
		keyActions[KeyEvent.KEYCODE_ENDCALL] = Actions.ALL_CLEAR
		keyActions[KeyEvent.KEYCODE_DEL] = Actions.BACKSPACE
		keyActions[KeyEvent.KEYCODE_ENTER] = Actions.EQUALS
	}
}
