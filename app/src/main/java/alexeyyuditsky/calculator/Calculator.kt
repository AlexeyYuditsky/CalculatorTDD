package alexeyyuditsky.calculator

import java.math.BigInteger

interface Calculator {

    fun plus(left: String, right: String): String

    fun minus(left: String, right: String): String

    class Base : Calculator {

        override fun plus(left: String, right: String): String =
            (BigInteger(left) + BigInteger(right)).toString()

        override fun minus(left: String, right: String): String =
            (BigInteger(left) - BigInteger(right)).toString()
    }
}