package alexeyyuditsky.calculator

import java.math.BigInteger

interface Calculator {

    fun sum(left: String, right: String): String

    fun diff(left: String, right: String): String

    companion object {
        fun create(): Calculator = DefaultCalculator()
    }
}

internal class DefaultCalculator : Calculator {

    override fun sum(left: String, right: String): String =
        (BigInteger(left) + BigInteger(right)).toString()

    override fun diff(left: String, right: String): String =
        (BigInteger(left) - BigInteger(right)).toString()
}