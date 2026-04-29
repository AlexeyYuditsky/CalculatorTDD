package alexeyyuditsky.calculator

interface CalculationState {

    fun handleNumber(
        number: String,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    )

    fun handleZero(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    )

    fun handlePlus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    )

    fun handleMinus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    )

    fun handleClear(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    )

    fun handleEquals(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback,
    ) = Unit
}

class DefineLeftPart : CalculationState {

    override fun handleNumber(
        number: String,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleZero(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handlePlus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleMinus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleClear(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }
}

class DefineOperationPart : CalculationState {

    override fun handleNumber(
        number: String,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleZero(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handlePlus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleMinus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleClear(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }
}

class DefineRightPart : CalculationState {

    override fun handleNumber(
        number: String,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleZero(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handlePlus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleMinus(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleClear(
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        TODO("Not yet implemented")
    }

    override fun handleEquals(
        calculator: Calculator,
        calculationParts: CalculationParts,
        calculatorUpdateCallback: CalculatorUpdateCallback
    ) {
        val result = when (calculationParts.operation) {
            Operation.PLUS -> calculator.sum(calculationParts.left, calculationParts.right)
            Operation.MINUS -> calculator.diff(calculationParts.left, calculationParts.right)
            else -> TODO()
        }
        calculatorUpdateCallback.updateCalculationResult(result)
        calculatorUpdateCallback.updateCalculationState(DefineLeftPart())
        calculatorUpdateCallback.updateCalculationParts(CalculationParts(left = result))
    }
}