package com.sidephone.calculator.ui.panels.t9

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sidephone.calculator.ui.components.Hint
import com.sidephone.calculator.ui.components.HintCell
import com.sidephone.calculator.ui.theme.Dimens

@Composable
fun HintsDpad(modifier: Modifier = Modifier, grid: List<List<Hint?>>) {
	Row(modifier = modifier) {
		grid.forEach { column ->
			Column(modifier = Modifier.padding(Dimens.hintCellSpacing / 2), verticalArrangement = Arrangement.spacedBy(Dimens.hintCellSpacing)) {
				column.forEach { hint -> HintCell(
					hint = hint,
					modifier = Modifier.weight(1f).width(Dimens.hintCellMinWidth)
				) }
			}
		}
	}
}
