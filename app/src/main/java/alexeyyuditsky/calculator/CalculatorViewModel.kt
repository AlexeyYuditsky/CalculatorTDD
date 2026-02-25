package alexeyyuditsky.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigInteger

class CalculatorViewModel : ViewModel(), CalculatorActions {

    private val mutableState = MutableStateFlow(CalculatorState())
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
        addToLeft = false
        mutableState.update { state ->
            val input = "${state.input}+"
            state.copy(input = input)
        }
    }

    override fun clickEquals() {
        mutableState.update { state ->
            val result = (BigInteger(left) + BigInteger(right)).toString()
            state.copy(result = result)
        }
    }
}