package com.sidephone.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.sidephone.calculator.ui.modifiers.tint
import com.sidephone.calculator.ui.theme.Dimens

data class Hint(
	val keySymbol: String,
	val functionLabelResId: Int,
	val functionLabelIsLarge: Boolean = false,
	val keyIsEmoji: Boolean = false,
	val keySlashed: Boolean = false,
	val primaryStyle: Boolean = false,
	val secondaryStyle: Boolean = false,
)


/**
 * One cell: key symbol and a function label. `hint == null` renders an
 * empty placeholder, so a grid keeps its shape even where a key has no hint.
 */
@Composable
fun HintCell(modifier: Modifier = Modifier, hint: Hint? = null) {
	val background = if (hint?.primaryStyle == true) {
		MaterialTheme.colorScheme.primary
	} else if (hint?.secondaryStyle == true) {
		MaterialTheme.colorScheme.secondary
	} else {
		MaterialTheme.colorScheme.tertiary
	}

	val textColor = if (hint?.primaryStyle == true) {
		MaterialTheme.colorScheme.onPrimary
	} else if (hint?.secondaryStyle == true) {
		MaterialTheme.colorScheme.onSecondary
	} else {
		MaterialTheme.colorScheme.onTertiary
	}

	Box(
		modifier = modifier
			.then(
				if (hint != null) {
					Modifier.background(background, MaterialTheme.shapes.small)
				} else {
					Modifier
				}
			)
			.padding(Dimens.hintCellPadding),
		contentAlignment = Alignment.Center
	) {
		if (hint == null) return@Box

		Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
			Box(contentAlignment = Alignment.Center) {
				val symbolModifier = if (hint.keyIsEmoji) {
					Modifier.tint(textColor)
				} else {
					Modifier
				}

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

			Text(
				text = stringResource(hint.functionLabelResId),
				style = if (hint.functionLabelIsLarge) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
				color = textColor,
				textAlign = TextAlign.Center,
				maxLines = 1,
				overflow = TextOverflow.Clip
			)
		}
	}
}
