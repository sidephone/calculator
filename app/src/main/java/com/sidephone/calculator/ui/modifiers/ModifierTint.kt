package com.sidephone.calculator.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer


/**
 * Tints the content of a composable with the given color. Useful for flattening colorful emojis,
 * and painting them in the text color.
 */
fun Modifier.tint(color: Color): Modifier = this
	.graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
	.drawWithContent {
		drawContent()
		drawRect(color, blendMode = BlendMode.SrcIn)
	}
