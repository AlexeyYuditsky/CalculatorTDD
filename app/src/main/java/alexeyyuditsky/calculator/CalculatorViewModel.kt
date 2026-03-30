package alexeyyuditsky.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val calculator: Calculator = Calculator.create(),
) : ViewModel(), CalculatorActions {

    private var addToLeft = true
    private var left = ""
    private var right = ""
    private var operation = ""

    private val mutableCalculatorState = MutableStateFlow(CalculatorState())
    val calculatorState = mutableCalculatorState.asStateFlow()

    override fun clickZero() {
        if (addToLeft) {
            if (left != "0" && left != "-") {
                left += "0"
                mutableCalculatorState.update { state ->
                    state.copy(input = left)
                }
            }
        } else {
            if (right != "0") {
                right += "0"
                mutableCalculatorState.update { state ->
                    state.copy(input = "$left$operation$right")
                }
            }
        }
    }

    override fun clickOne() {
        if (addToLeft) {
            if (left == "0") {
                left = "1"
            } else {
                left += "1"
            }
            mutableCalculatorState.update { state ->
                state.copy(input = left)
            }
        } else {
            if (right == "0") {
                right = "1"
            } else {
                right += "1"
            }
            mutableCalculatorState.update { state ->
                state.copy(input = "$left$operation$right")
            }
        }
    }

    override fun clickTwo() {
        if (addToLeft) {
            if (left == "0") {
                left = "2"
            } else {
                left += "2"
            }
            mutableCalculatorState.update { state ->
                state.copy(input = left)
            }
        } else {
            if (right == "0") {
                right = "2"
            } else {
                right += "2"
            }
            mutableCalculatorState.update { state ->
                state.copy(input = "$left$operation$right")
            }
        }
    }

    override fun clickPlus() {
        operation = "+"
        mutableCalculatorState.update { state ->
            if (state.input.isEmpty() || state.input.endsWith("+")) {
                return
            } else if (left.isNotEmpty() && right.isNotEmpty()) {
                left = calculator.sum(left, right)
                right = ""
                state.copy(
                    input = "${left}+",
                    result = ""
                )
            } else {
                addToLeft = false
                state.copy(input = "${state.input}+")
            }
        }
    }

    override fun clickMinus() {
        operation = "-"
        mutableCalculatorState.update { state ->
            if (state.input.isEmpty()) {
                left = "-"
                state.copy(input = left)
            } else if (state.input.endsWith("-")) {
                return
            } else if (left.isNotEmpty() && right.isNotEmpty()) {
                left = calculator.diff(left, right)
                right = ""
                state.copy(
                    input = "${left}-",
                    result = ""
                )
            } else {
                addToLeft = false
                state.copy(input = "${state.input}-")
            }
        }
    }

    override fun clickEquals() {
        mutableCalculatorState.update { state ->
            if (
                left.isEmpty() ||
                right.isEmpty() ||
                state.result.isNotEmpty()
            ) return

            val result = when (operation) {
                "+" -> calculator.sum(left, right)
                "-" -> calculator.diff(left, right)
                else -> ""
            }
            operation = ""
            state.copy(result = result)
        }
    }

    override fun clickClear() {
        left = ""
        right = ""
        operation = ""
        addToLeft = true
        mutableCalculatorState.update { CalculatorState() }
    }
}