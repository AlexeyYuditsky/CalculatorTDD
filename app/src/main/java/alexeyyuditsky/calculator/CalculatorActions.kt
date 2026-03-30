package alexeyyuditsky.calculator

interface CalculatorActions {

    fun clickZero()
    fun clickOne()
    fun clickTwo()

    fun clickPlus()
    fun clickMinus()
    fun clickEquals()
    fun clickClear()

    object Empty : CalculatorActions {
        override fun clickZero() = Unit
        override fun clickOne() = Unit
        override fun clickTwo() = Unit
        override fun clickPlus() = Unit
        override fun clickMinus() = Unit
        override fun clickEquals() = Unit
        override fun clickClear() = Unit
    }
}