package alexeyyuditsky.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val calculator: Calculator = DefaultCalculator(),
) : ViewModel(), CalculatorActions, CalculatorUpdateCallback {

    private var calculationState: CalculationState = DefineLeftPart()
    private var calculationParts = CalculationParts()

    private val mutableCalculatorState = MutableStateFlow(CalculatorState())
    val calculatorState = mutableCalculatorState.asStateFlow()

    override fun clickZero() =
        calculationState.handleZero(calculationParts, this)

    override fun clickOne() =
        calculationState.handleNumber("1", calculationParts, this)

    override fun clickTwo() =
        calculationState.handleNumber("2", calculationParts, this)

    override fun clickPlus() =
        calculationState.handlePlus(calculator, calculationParts, this)

    override fun clickMinus() =
        calculationState.handleMinus(calculator, calculationParts, this)

    override fun clickEquals() =
        calculationState.handleEquals(calculator, calculationParts, this)

    override fun clickClear() =
        calculationState.handleClear(calculationParts, this)

    override fun updateCalculationParts(calculationParts: CalculationParts) {
        this.calculationParts = calculationParts
    }

    override fun updateCalculationState(calculationState: CalculationState) {
        this.calculationState = calculationState
    }

    override fun updateCalculationInput() =
        mutableCalculatorState.update { state ->
            state.copy(input = calculationParts.value)
        }

    override fun updateCalculationResult(calculationResult: String) =
        mutableCalculatorState.update { state ->
            state.copy(result = calculationResult)
        }
}