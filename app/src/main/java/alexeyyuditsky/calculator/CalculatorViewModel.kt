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

    override fun clickZero() {
        if (addToLeft) {
            if (left != "0") {
                left += "0"
                mutableState.update { state ->
                    val input = left
                    state.copy(input = input)
                }
            }
        } else {
            if (right != "0") {
                right += "0"
                mutableState.update { state ->
                    val input = "$left+$right"
                    state.copy(input = input)
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
                val input = left
                state.copy(input = input)
            }
        } else {
            if (right == "0") {
                right = "1"
            } else {
                right += "1"
            }
            mutableState.update { state ->
                val input = "$left+$right"
                state.copy(input = input)
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
                val input = left
                state.copy(input = input)
            }
        } else {
            if (right == "0") {
                right = "2"
            } else {
                right += "2"
            }
            mutableState.update { state ->
                val input = "$left+$right"
                state.copy(input = input)
            }
        }
    }

    override fun clickPlus() {
        mutableState.update { state ->
            if (state.input.isEmpty() || state.input.endsWith("+")) {
                return
            } else if (left.isNotEmpty() && right.isNotEmpty()) {
                left = calculator.sum(left, right)
                right = ""
                val input = "${left}+"
                state.copy(
                    input = input,
                    result = ""
                )
            } else {
                addToLeft = false
                val input = "${state.input}+"
                state.copy(input = input)
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

            val result = calculator.sum(left, right)
            state.copy(result = result)
        }
    }
}