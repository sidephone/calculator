package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.os.Build
import android.view.InputDevice
import android.view.KeyCharacterMap
import android.view.KeyEvent
import java.util.Locale

/**
 * This adds support for non-Sidephone keypads. Otherwise, it is not necessary.
 */
abstract class ForeignKeypad(inputManager: InputManager) : Keypad(inputManager) {
	// for foreign T9 keypad detection, check if the device has dpad keys
	private val dpadKeys = listOf(
		KeyEvent.KEYCODE_DPAD_UP,
		KeyEvent.KEYCODE_DPAD_DOWN,
		KeyEvent.KEYCODE_DPAD_LEFT,
		KeyEvent.KEYCODE_DPAD_RIGHT,
	).toIntArray()

	private val isSidephone = Build.MANUFACTURER.uppercase(Locale.US) == "SIDEPHONE"


	override fun detect(device: InputDevice?) {
		if (isSidephone) {
			super.detect(device)
			return
		}

		val oldLayout = layoutState.value

		layoutState.value = if (dpadKeys.map { KeyCharacterMap.deviceHasKey(it) }.all { it }) {
			Layout.T9
		} else {
			Layout.NONE
		}

		if (oldLayout != layoutState.value) {
			onChange()
		}
	}


	override fun listenForChanges() {
		if (isSidephone) super.listenForChanges()
	}

	override fun stopListening() {
		if (isSidephone) super.stopListening()
	}
}
