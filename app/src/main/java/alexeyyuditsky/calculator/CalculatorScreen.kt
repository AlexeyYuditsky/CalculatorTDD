package alexeyyuditsky.calculator

import alexeyyuditsky.calculator.ui.theme.CalculatorTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorScreen(
    calculatorState: CalculatorState,
    calculatorActions: CalculatorActions,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = calculatorState.input,
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("input")
        )
        Text(
            text = calculatorState.result,
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("result")
        )

        Row {
            CalculatorButton(
                text = "0",
                testTag = "zero",
                onClick = calculatorActions::clickZero
            )
            CalculatorButton(
                text = "1",
                testTag = "one",
                onClick = calculatorActions::clickOne
            )
            CalculatorButton(
                text = "2",
                testTag = "two",
                onClick = calculatorActions::clickTwo
            )
        }
        Row {
            CalculatorButton(
                text = "+",
                testTag = "plus",
                onClick = calculatorActions::clickPlus
            )
            CalculatorButton(
                text = "-",
                testTag = "minus",
                onClick = calculatorActions::clickMinus
            )
            CalculatorButton(
                text = "=",
                testTag = "equals",
                onClick = calculatorActions::clickEquals
            )
            CalculatorButton(
                text = "C",
                testTag = "clear",
                onClick = calculatorActions::clickClear
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorScreen() = CalculatorTheme {
    CalculatorScreen(
        calculatorState = CalculatorState(input = "1+2", result = "3"),
        calculatorActions = CalculatorActions.Empty
    )
}

@Composable
private fun CalculatorButton(
    text: String,
    testTag: String,
    onClick: () -> Unit,
) = Button(
    onClick = onClick,
    modifier = Modifier
        .testTag(testTag)
        .padding(8.dp)
) {
    Text(
        text = text,
        fontSize = 24.sp
    )
}