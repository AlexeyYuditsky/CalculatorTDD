package alexeyyuditsky.calculator

import java.math.BigInteger

interface Calculator {

    fun sum(left:String, right: String): String

    class Base : Calculator {

        override fun sum(left: String, right: String): String {
           return (BigInteger(left) + BigInteger(right)).toString()
        }
    }
}