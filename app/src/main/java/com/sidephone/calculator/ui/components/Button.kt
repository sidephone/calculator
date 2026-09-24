package com.sidephone.calculator.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.sidephone.calculator.ui.theme.Dimens
import androidx.compose.material3.Button as M3Button

@Composable
fun Button(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	textResId: Int = 0,
	text: String = "",
	background: Color = MaterialTheme.colorScheme.tertiary,
	textColor: Color = MaterialTheme.colorScheme.onTertiary,
	textSize: TextUnit = Dimens.buttonTextSize
) {
	M3Button(
		onClick = onClick,
		modifier = modifier.padding(Dimens.buttonPadding),
		contentPadding = PaddingValues(0.dp),
		colors = ButtonDefaults.buttonColors(containerColor = background, contentColor = textColor)
	) {
		Text(
			fontSize = textSize,
			text = if (textResId != 0) stringResource(id = textResId) else text
		)
	}
}
