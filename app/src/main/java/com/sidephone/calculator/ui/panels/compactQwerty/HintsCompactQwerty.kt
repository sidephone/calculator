package com.sidephone.calculator.ui.panels.compactQwerty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.components.HintCell
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun HintsCompactQwerty(grid: List<List<Hint?>>, modifier: Modifier = Modifier) {
	Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Dimens.hintCellSpacing)) {
		grid.forEach { row ->
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.hintCellSpacing)) {
				row.forEach { hint -> HintCell(hint = hint, padding = Dimens.hintCellQwertyPadding, modifier = Modifier.weight(1f)) }
			}
		}
	}
}
