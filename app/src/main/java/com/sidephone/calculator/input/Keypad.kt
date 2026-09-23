package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Keypad(private val inputManager: InputManager) {
	enum class TYPE { COMPACT_QWERTY, GAMEPAD, NONE, T9 }

	private val types = mapOf(
		"d9bf35cac6ea4aa8e3d2e56aaf6548022165ff41" to TYPE.COMPACT_QWERTY, // gxa535_qwerty
		"f039f068d76b8ac29f9727f377ef44ee36f8876a" to TYPE.GAMEPAD, // // gxa535_gamepad
		"a2169ecfa473854b09588692a30fe13505a1336f" to TYPE.T9 // gxa535_phone
	)

	private val changeListener = object : InputManager.InputDeviceListener {
		override fun onInputDeviceAdded(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
		override fun onInputDeviceRemoved(deviceId: Int) { _current.value = TYPE.NONE }
		override fun onInputDeviceChanged(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
	}

	private val _current = MutableStateFlow(TYPE.NONE)
	val current: StateFlow<TYPE> = _current


	fun detect() {
		inputManager
			.inputDeviceIds
			.map(InputDevice::getDevice)
			.forEach { detect(it) }
	}


	private fun detect(device: InputDevice?) {
		_current.value = TYPE.NONE

		if (
			device == null
			|| device.isVirtual
			|| !device.supportsSource(InputDevice.SOURCE_KEYBOARD)
		)
			return

		_current.value = types[device.descriptor] ?: TYPE.NONE
	}

	fun listenForChanges() {
		inputManager.registerInputDeviceListener(changeListener, Handler(Looper.getMainLooper()))
	}

	fun stopListening() {
		inputManager.unregisterInputDeviceListener(changeListener)
	}
}
