package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Keypad(private val inputManager: InputManager) {
	enum class LAYOUT { COMPACT_QWERTY, GAMEPAD, NONE, T9 }

	private val layouts = mapOf(
		"d9bf35cac6ea4aa8e3d2e56aaf6548022165ff41" to LAYOUT.COMPACT_QWERTY, // gxa535_qwerty
		"f039f068d76b8ac29f9727f377ef44ee36f8876a" to LAYOUT.GAMEPAD, // // gxa535_gamepad
		"a2169ecfa473854b09588692a30fe13505a1336f" to LAYOUT.T9 // gxa535_phone
	)

	private val changeListener = object : InputManager.InputDeviceListener {
		override fun onInputDeviceAdded(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
		override fun onInputDeviceRemoved(deviceId: Int) { _layout.value = LAYOUT.NONE; onChange() }
		override fun onInputDeviceChanged(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
	}

	private val _layout = MutableStateFlow(LAYOUT.NONE)
	val layout: StateFlow<LAYOUT> = _layout


	abstract fun onChange()


	fun detect() {
		inputManager
			.inputDeviceIds
			.map(InputDevice::getDevice)
			.forEach { detect(it) }
	}


	private fun detect(device: InputDevice?) {
		val oldType = _layout.value

		_layout.value = LAYOUT.NONE

		if (
			device != null
			&& !device.isVirtual
			&& device.supportsSource(InputDevice.SOURCE_KEYBOARD)
		) {
			_layout.value = layouts[device.descriptor] ?: LAYOUT.NONE
		}

		if (oldType != _layout.value)
			onChange()
	}


	fun listenForChanges() {
		inputManager.registerInputDeviceListener(changeListener, Handler(Looper.getMainLooper()))
	}


	fun stopListening() {
		inputManager.unregisterInputDeviceListener(changeListener)
	}
}
