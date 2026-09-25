package com.sidephone.calculator.input

import android.hardware.input.InputManager
import android.os.Handler
import android.os.Looper
import android.view.InputDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SidephoneKeypad(private val inputManager: InputManager) {
	enum class Layout { COMPACT_QWERTY, GAMEPAD, NONE, T9, UNKNOWN }

	private val layouts = mapOf(
		"d9bf35cac6ea4aa8e3d2e56aaf6548022165ff41" to Layout.COMPACT_QWERTY, // gxa535_qwerty
		"f039f068d76b8ac29f9727f377ef44ee36f8876a" to Layout.GAMEPAD, // // gxa535_gamepad
		"a2169ecfa473854b09588692a30fe13505a1336f" to Layout.T9 // gxa535_phone
	)

	private var changeListener: InputManager.InputDeviceListener? = null
	protected val _layout = MutableStateFlow(Layout.NONE)
	val layout: StateFlow<Layout> = _layout


	abstract fun onChange()


	init {
		_layout.value = Layout.UNKNOWN
	}


	fun detect() {
		inputManager
			.inputDeviceIds
			.map(InputDevice::getDevice)
			.forEach { detect(it) }
	}


	protected open fun detect(device: InputDevice?) {
		val oldLayout = _layout.value

		_layout.value = Layout.NONE

		_layout.value = if (
			device != null
			&& !device.isVirtual
			&& device.supportsSource(InputDevice.SOURCE_KEYBOARD)
		) {
			layouts[device.descriptor] ?: Layout.NONE
		} else {
			Layout.NONE
		}

		if (oldLayout != _layout.value) {
			onChange()
		}
	}


	open fun listenForChanges() {
		if (changeListener == null) {
			changeListener = object : InputManager.InputDeviceListener {
				override fun onInputDeviceAdded(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
				override fun onInputDeviceRemoved(deviceId: Int) { _layout.value = Layout.NONE; onChange() }
				override fun onInputDeviceChanged(deviceId: Int) { detect(InputDevice.getDevice(deviceId)) }
			}
		}

		inputManager.registerInputDeviceListener(changeListener, Handler(Looper.getMainLooper()))
	}

	open fun stopListening() {
		if (changeListener != null) {
			inputManager.unregisterInputDeviceListener(changeListener)
		}
	}
}
