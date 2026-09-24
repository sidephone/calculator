package com.sidephone.calculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sidephone.calculator.R
import com.sidephone.calculator.calculator.Action
import com.sidephone.calculator.calculator.Calculator
import com.sidephone.calculator.ui.components.Button
import com.sidephone.calculator.ui.components.ButtonAC
import com.sidephone.calculator.ui.components.ButtonEquals
import com.sidephone.calculator.ui.components.ButtonLargeText
import com.sidephone.calculator.ui.components.ButtonPeriod
import com.sidephone.calculator.ui.components.ExpressionField

@Composable
fun TouchScreen(modifier: Modifier, calculator: Calculator) {
	Column(
		modifier = modifier.background(color = MaterialTheme.colorScheme.background)
	) {
		ExpressionField(calculator.expression.collectAsState().value)
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			val buttonModifier = Modifier.weight(1f)

			Row {
				ButtonAC(modifier = buttonModifier) { calculator.onAction(Action.ALL_CLEAR) }
				Button(modifier = buttonModifier, textResId = R.string.button_parenthesis_left, onClick = { calculator.onAction(Action.LEFT_PAREN) })
				Button(modifier = buttonModifier, textResId = R.string.button_parenthesis_right, onClick = { calculator.onAction(Action.RIGHT_PAREN) })
				ButtonLargeText(modifier = buttonModifier, textResId = R.string.button_divide, onClick = { calculator.onAction(Action.DIVIDE) })
			}
			Row {
				Button(modifier = buttonModifier, textResId = R.string.button_7, onClick = { calculator.onAction(Action.NUM_7) })
				Button(modifier = buttonModifier, textResId = R.string.button_8, onClick = { calculator.onAction(Action.NUM_8) })
				Button(modifier = buttonModifier, textResId = R.string.button_9, onClick = { calculator.onAction(Action.NUM_9) })
				ButtonLargeText(modifier = buttonModifier, textResId = R.string.button_multiply, onClick = { calculator.onAction(Action.MULTIPLY) })
			}
			Row {
				Button(modifier = buttonModifier, textResId = R.string.button_4, onClick = { calculator.onAction(Action.NUM_4) })
				Button(modifier = buttonModifier, textResId = R.string.button_5, onClick = { calculator.onAction(Action.NUM_5) })
				Button(modifier = buttonModifier, textResId = R.string.button_6, onClick = { calculator.onAction(Action.NUM_6) })
				ButtonLargeText(modifier = buttonModifier, textResId = R.string.button_subtract, onClick = { calculator.onAction(Action.SUBTRACT) })
			}
			Row {
				Button(modifier = buttonModifier, textResId = R.string.button_1, onClick = { calculator.onAction(Action.NUM_1) })
				Button(modifier = buttonModifier, textResId = R.string.button_2, onClick = { calculator.onAction(Action.NUM_2) })
				Button(modifier = buttonModifier, textResId = R.string.button_3, onClick = { calculator.onAction(Action.NUM_3) })
				ButtonLargeText(modifier = buttonModifier, textResId = R.string.button_add, onClick = { calculator.onAction(Action.ADD) })
			}
			Row {
				Button(modifier = buttonModifier, textResId = R.string.button_0, onClick = { calculator.onAction(Action.NUM_0) })
				ButtonPeriod(modifier = buttonModifier, onClick = { calculator.onAction(Action.PERIOD) })
				Button(modifier = buttonModifier, textResId = R.string.button_backspace, onClick = { calculator.onAction(Action.BACKSPACE) })
				ButtonEquals(modifier = buttonModifier) { calculator.onAction(Action.EQUALS) }
			}
		}
	}
}
