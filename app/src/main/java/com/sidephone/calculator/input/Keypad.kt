package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import android.view.KeyCharacterMap
import android.view.KeyEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

abstract class Keypad(private val inputManager: InputManager) {
	enum class Layout { COMPACT_QWERTY, GAMEPAD, NONE, T9 }

	// native Sidephone keypads
	private val layouts = mapOf(
		"d9bf35cac6ea4aa8e3d2e56aaf6548022165ff41" to Layout.COMPACT_QWERTY, // gxa535_qwerty
		"f039f068d76b8ac29f9727f377ef44ee36f8876a" to Layout.GAMEPAD, // // gxa535_gamepad
		"a2169ecfa473854b09588692a30fe13505a1336f" to Layout.T9 // gxa535_phone
	)

	// for foreign T9 keypad detection, check if the device has dpad keys
	private val dpadKeys = listOf(
		KeyEvent.KEYCODE_DPAD_UP,
		KeyEvent.KEYCODE_DPAD_DOWN,
		KeyEvent.KEYCODE_DPAD_LEFT,
		KeyEvent.KEYCODE_DPAD_RIGHT,
	).toIntArray()

	private var changeListener: InputManager.InputDeviceListener? = null
	private val isSidephone = Build.MANUFACTURER.uppercase(Locale.US) == "SIDEPHONE"
	private val _layout = MutableStateFlow(Layout.NONE)
	val layout: StateFlow<Layout> = _layout


	abstract fun onChange()


	fun detect() {
		inputManager
			.inputDeviceIds
			.map(InputDevice::getDevice)
			.forEach { detect(it) }
	}


	private fun detect(device: InputDevice?) {
		val oldLayout = _layout.value

		_layout.value = if (isSidephone) {
			detectSidephoneLayout(device)
		} else {
			detectForeignLayout()
		}

		if (oldLayout != _layout.value) {
			onChange()
		}
	}


	private fun detectSidephoneLayout(device: InputDevice?): Layout {
		if (
			device != null
			&& !device.isVirtual
			&& device.supportsSource(InputDevice.SOURCE_KEYBOARD)
		) {
			return layouts[device.descriptor] ?: Layout.NONE
		}

		return Layout.NONE
	}


	private fun detectForeignLayout(): Layout {
		if (dpadKeys.map { KeyCharacterMap.deviceHasKey(it) }.all { it }) {
			return Layout.T9
		}

		return Layout.NONE
	}


	fun listenForChanges() {
		if (!isSidephone) return

		if (changeListener == null) {
			changeListener = object : InputManager.InputDeviceListener {
				override fun onInputDeviceAdded(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
				override fun onInputDeviceRemoved(deviceId: Int) { _layout.value = Layout.NONE; onChange() }
				override fun onInputDeviceChanged(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
			}
		}

		inputManager.registerInputDeviceListener(changeListener, Handler(Looper.getMainLooper()))
	}

	fun stopListening() {
		if (isSidephone && changeListener != null) {
			inputManager.unregisterInputDeviceListener(changeListener)
		}
	}
}
