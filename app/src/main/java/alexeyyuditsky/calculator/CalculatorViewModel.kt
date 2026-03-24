package alexeyyuditsky.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val calculator: Calculator = Calculator.Base(),
) : ViewModel(), Actions {

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    private var addToLeft = true
    private var left = ""
    private var right = ""
    private var operation = ""

    override fun clickZero() {
        if (addToLeft) {
            if (left != "0") {
                left += "0"
                mutableState.update { state ->
                    state.copy(input = left)
                }
            }
        } else {
            if (right != "0") {
                right += "0"
                mutableState.update { state ->
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
            mutableState.update { state ->
                state.copy(input = left)
            }
        } else {
            if (right == "0") {
                right = "1"
            } else {
                right += "1"
            }
            mutableState.update { state ->
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
            mutableState.update { state ->
                state.copy(input = left)
            }
        } else {
            if (right == "0") {
                right = "2"
            } else {
                right += "2"
            }
            mutableState.update { state ->
                state.copy(input = "$left$operation$right")
            }
        }
    }

    override fun clickPlus() {
        operation = "+"
        mutableState.update { state ->
            if (state.input.isEmpty() || state.input.endsWith("+")) {
                return
            } else if (left.isNotEmpty() && right.isNotEmpty()) {
                left = calculator.plus(left, right)
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
        mutableState.update { state ->
            if (state.input.isEmpty()) {
                left = "-"
                state.copy(input = left)
            } else if (state.input.endsWith("-")) {
                return
            } else if (left.isNotEmpty() && right.isNotEmpty()) {
                left = calculator.minus(left, right)
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
        mutableState.update { state ->
            if (
                left.isEmpty() ||
                right.isEmpty() ||
                state.result.isNotEmpty()
            ) return

            val result = when (operation) {
                "+" -> calculator.plus(left, right)
                "-" -> calculator.minus(left, right)
                else -> throw IllegalStateException()
            }
            operation = ""
            state.copy(result = result)
        }
    }
}