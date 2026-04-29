package alexeyyuditsky.calculator

class CalculationParts(
    val left: String = "",
    val operation: Operation = Operation.EMPTY,
    val right: String = "",
) {

    val value: String get() = "$left$operation$right"
}