package com.sidephone.calculator.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val LightColorScheme = lightColorScheme(
	// expression
	background = Color(0xFFFAFAFA),
	onBackground = Color(0xFF252A2C),

	// "=" button
	primary = Color(0xFF008A7A),
	onPrimary = Color(0XFFFFFFFF),

	// "AC" button
	secondary = Color(0xFFD5EFEB),
	onSecondary = Color(0xFF164E49),

	// all other buttons
	tertiary = Color(0xFFE8E9EB),
	onTertiary = Color(0xFF303438),
)

val DarkColorScheme = darkColorScheme(
	// expression
	background  = Color(0xFF121212),
	onBackground = Color(0xFFDEE3E5),

	// "=" button
	primary = Color(0xFF00796B),
	onPrimary = Color(0xFFFFFFFF),

	// "AC" button
	secondary = Color(0xFF004E5A),
	onSecondary = Color(0xFFD0E8E9),

	// all other buttons
	tertiary = Color(0xFF37393E),
	onTertiary = Color(0xFFDEE3E5),
)

const val DISABLED_ALPHA = 0.4f
