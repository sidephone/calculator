package com.sidephone.calculator.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.sidephone.calculator.ui.modifiers.tint


@Composable
fun HintCellSymbol(hint: Hint, textColor: Color) {
	if (hint.keySymbol == null) return

	val symbolModifier = if (hint.keyIsEmoji) {
		Modifier.tint(textColor)
	} else {
		Modifier
	}

	Box(contentAlignment = Alignment.Center) {
		Text(
			modifier = symbolModifier,
			text = hint.keySymbol,
			style = if (hint.keyIsEmoji) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium,
			color = textColor,
			textAlign = TextAlign.Center,
			maxLines = 1,
			overflow = TextOverflow.Clip
		)

		if (hint.keySlashed) {
			Text(
				"/",
				color = textColor,
				style = MaterialTheme.typography.labelLarge,
				modifier = Modifier.graphicsLayer { rotationZ = 20f }
			)
		}
	}

	Text(
		text = " ➞ ",
		style = MaterialTheme.typography.titleMedium,
		color = textColor,
		textAlign = TextAlign.Center,
		maxLines = 1,
		overflow = TextOverflow.Clip
	)
}
