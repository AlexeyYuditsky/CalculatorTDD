package alexeyyuditsky.calculator

interface Actions {

    fun clickZero()
    fun clickOne()
    fun clickTwo()

    fun clickPlus()
    fun clickMinus()
    fun clickEquals()

    object Empty : Actions {
        override fun clickZero() {}
        override fun clickOne() {}
        override fun clickTwo() {}
        override fun clickPlus() {}
        override fun clickMinus() {}
        override fun clickEquals() {}
    }
}