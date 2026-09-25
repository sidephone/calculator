package com.sidephone.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import com.sidephone.calculator.ui.theme.Dimens

data class Hint(
	val functionLabelResId: Int,
	val functionLabelIsLarge: Boolean = false,
	val keySymbol: String? = null,
	val keyIsEmoji: Boolean = false,
	val keySlashed: Boolean = false,
	val primaryStyle: Boolean = false,
	val secondaryStyle: Boolean = false,
	val onClick: (() -> Unit)? = null
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
			.then(
				if (hint?.onClick != null) {
					Modifier
						.focusProperties { canFocus = false }
						.clickable(onClick = hint.onClick)
				} else {
					Modifier
				}
			)
			.padding(Dimens.hintCellPadding),
		contentAlignment = Alignment.Center
	) {
		if (hint == null) return@Box

		Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
			HintCellSymbol(hint, textColor)
			HintCellFunction(hint, textColor)
		}
	}
}
