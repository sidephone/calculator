package com.sidephone.calculator.ui.panels.t9

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.components.HintCell
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun HintsT9Side(modifier: Modifier = Modifier, top: Hint, middle: Hint, bottom: Hint) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.spacedBy(Dimens.hintCellSpacing)
	) {
		HintCell(modifier = Modifier.weight(1.6f).width(Dimens.hintCellMinWidth), hint = top)
		HintCell(modifier = Modifier.weight(1.6f).width(Dimens.hintCellMinWidth), hint = middle)
		Box(modifier = Modifier.weight(0.55f).width(Dimens.hintCellMinWidth))
		HintCell(modifier = Modifier.weight(1.25f).width(Dimens.hintCellMinWidth), hint = bottom)
	}
}
