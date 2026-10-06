package com.sidephone.calculator.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
	val buttonPadding = 2.dp
	val buttonTextSize = 20.sp
	val buttonLargeTextSize = 26.sp

	val dividerHorizontal = 8.dp
	val dividerVertical = 12.dp

	val expressionFontSizeDefault = 36.sp
	val expressionFontSizeCompactQwerty = mapOf(
		42 to expressionFontSizeDefault,
		95 to 24.sp,
		150 to 16.sp,
		Int.MAX_VALUE to 11.sp
	)
	val expressionFontSizeT9 = mapOf(
		56 to expressionFontSizeDefault,
		95 to 24.sp,
		Int.MAX_VALUE to 16.sp
	)
	val expressionFontSizeTouchscreen = mapOf(
		14 to expressionFontSizeDefault,
		38 to 24.sp,
		75 to 16.sp,
		Int.MAX_VALUE to 11.sp
	)
	val expressionLineSizeMultiplier = 1.2 // font size * this = line height
	val expressionPadding = 16.dp
	val expressionPaddingBottom = 8.dp

	val hintCellBorderRadius = 20.dp
	val hintCellMinWidth = 56.dp
	val hintCellSpacing = 4.dp
	val hintCellQwertyPadding = 0.dp
	val hintCellT9Padding = 4.dp
}
