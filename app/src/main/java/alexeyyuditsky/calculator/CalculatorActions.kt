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
        override fun clickZero() {}
        override fun clickOne() {}
        override fun clickTwo() {}
        override fun clickPlus() {}
        override fun clickMinus() {}
        override fun clickEquals() {}
        override fun clickClear() {}
    }
}