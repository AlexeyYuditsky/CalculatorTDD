package alexeyyuditsky.calculator

interface CalculatorUpdateCallback {

    fun updateCalculationParts(calculationParts: CalculationParts)

    fun updateCalculationState(calculationState: CalculationState)

    fun updateCalculationResult(calculationResult: String)

    fun updateCalculationInput()
}