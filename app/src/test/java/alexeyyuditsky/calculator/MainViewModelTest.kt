package alexeyyuditsky.calculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val calculator = FakeCalculator()
    private val viewModel = CalculatorViewModel(calculator = calculator)

    @Before
    fun setup() = with(viewModel) {
        assertEquals("", state.value.input)
        assertEquals("", state.value.result)
    }

    @Test
    fun sum_of_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", state.value.input)

        clickPlus()
        assertEquals("1+", state.value.input)

        clickTwo()
        assertEquals("1+2", state.value.input)

        clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun sum_of_numbers_more_complex() = with(viewModel) {
        clickTwo()
        assertEquals("2", state.value.input)

        clickOne()
        assertEquals("21", state.value.input)

        clickZero()
        assertEquals("210", state.value.input)

        clickZero()
        assertEquals("2100", state.value.input)

        clickPlus()
        assertEquals("2100+", state.value.input)

        clickOne()
        assertEquals("2100+1", state.value.input)

        clickTwo()
        assertEquals("2100+12", state.value.input)

        clickZero()
        assertEquals("2100+120", state.value.input)

        clickZero()
        assertEquals("2100+1200", state.value.input)

        clickEquals()
        assertEquals("2100+1200", state.value.input)
        assertEquals("3300", state.value.result)
    }

    @Test
    fun sum_of_two_numbers_corner_case() = with(viewModel) {
        clickOne()
        assertEquals("1", state.value.input)

        var expected = "1"
        repeat(9) {
            clickZero()
            expected += 0
            assertEquals(expected, state.value.input)
        }
        assertEquals("1000000000", state.value.input)

        clickPlus()
        assertEquals("1000000000+", state.value.input)

        clickTwo()
        assertEquals("1000000000+2", state.value.input)

        expected = "1000000000+2"
        repeat(9) {
            clickZero()
            expected += 0
            assertEquals(expected, state.value.input)
        }
        assertEquals("1000000000+2000000000", state.value.input)

        clickEquals()
        assertEquals("1000000000+2000000000", state.value.input)
        assertEquals("3000000000", state.value.result)
    }

    @Test
    fun prevent_multiple_zeros_plus_operation() = with(viewModel) {
        repeat(3) {
            clickZero()
            assertEquals("0", state.value.input)
        }

        clickPlus()
        assertEquals("0+", state.value.input)

        repeat(3) {
            clickZero()
            assertEquals("0+0", state.value.input)
        }

        clickEquals()
        assertEquals("0+0", state.value.input)
        assertEquals("0", state.value.result)
    }

    @Test
    fun prevent_leading_zeros() = with(viewModel) {
        clickZero()
        assertEquals("0", state.value.input)

        clickOne()
        assertEquals("1", state.value.input)

        clickPlus()
        assertEquals("1+", state.value.input)

        clickZero()
        assertEquals("1+0", state.value.input)

        clickTwo()
        assertEquals("1+2", state.value.input)

        clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun prevent_multiple_plus_operation() = with(viewModel) {
        clickTwo()
        assertEquals("2", state.value.input)

        repeat(3) {
            clickPlus()
            assertEquals("2+", state.value.input)
        }

        clickOne()
        assertEquals("2+1", state.value.input)

        clickEquals()
        assertEquals("2+1", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun prevent_leading_pluses() = with(viewModel) {
        repeat(3) {
            clickPlus()
            assertEquals("", state.value.input)
        }

        clickTwo()
        assertEquals("2", state.value.input)

        clickPlus()
        assertEquals("2+", state.value.input)

        clickOne()
        assertEquals("2+1", state.value.input)

        clickEquals()
        assertEquals("2+1", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun sum_of_more_than_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", state.value.input)
        assertEquals("", state.value.result)

        clickPlus()
        assertEquals("1+", state.value.input)
        assertEquals("", state.value.result)

        clickTwo()
        assertEquals("1+2", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            clickPlus()
            calculator.assertPlusCalled(expectedTimes = 1)
            assertEquals("3+", state.value.input)
            assertEquals("", state.value.result)
        }

        clickOne()
        assertEquals("3+1", state.value.input)
        assertEquals("", state.value.result)

        clickZero()
        assertEquals("3+10", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            clickPlus()
            calculator.assertPlusCalled(expectedTimes = 2)
            assertEquals("13+", state.value.input)
            assertEquals("", state.value.result)
        }

        clickTwo()
        assertEquals("13+2", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            clickEquals()
            calculator.assertPlusCalled(expectedTimes = 3)
            assertEquals("13+2", state.value.input)
            assertEquals("15", state.value.result)
        }
    }

    @Test
    fun sum_after_equals() = with(viewModel) {
        clickOne()
        assertEquals("1", state.value.input)

        clickPlus()
        assertEquals("1+", state.value.input)

        clickTwo()
        assertEquals("1+2", state.value.input)

        clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)

        clickPlus()
        assertEquals("3+", state.value.input)
        assertEquals("", state.value.result)

        clickOne()
        assertEquals("3+1", state.value.input)
        assertEquals("", state.value.result)

        clickEquals()
        assertEquals("3+1", state.value.input)
        assertEquals("4", state.value.result)

        clickPlus()
        assertEquals("4+", state.value.input)
        assertEquals("", state.value.result)

        clickTwo()
        assertEquals("4+2", state.value.input)
        assertEquals("", state.value.result)

        clickPlus()
        assertEquals("6+", state.value.input)
        assertEquals("", state.value.result)

        clickOne()
        assertEquals("6+1", state.value.input)
        assertEquals("", state.value.result)

        clickEquals()
        assertEquals("6+1", state.value.input)
        assertEquals("7", state.value.result)
    }

    @Test
    fun prevent_equals_not_at_the_end() = with(viewModel) {
        repeat(3) {
            clickEquals()
            assertEquals("", state.value.input)
            assertEquals("", state.value.result)
        }

        clickTwo()
        assertEquals("2", state.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2", state.value.input)
            assertEquals("", state.value.result)
        }

        clickPlus()
        assertEquals("2+", state.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2+", state.value.input)
            assertEquals("", state.value.result)
        }

        clickOne()
        assertEquals("2+1", state.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2+1", state.value.input)
            assertEquals("3", state.value.result)
        }
        calculator.assertPlusCalled(expectedTimes = 1)
    }

    @Test
    fun minus_of_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", state.value.input)

        clickMinus()
        assertEquals("1-", state.value.input)

        clickTwo()
        assertEquals("1-2", state.value.input)

        clickEquals()
        assertEquals("1-2", state.value.input)
        assertEquals("-1", state.value.result)
    }

    @Test
    fun minus_sign_ahead() = with(viewModel) {
        clickMinus()
        assertEquals("-", state.value.input)

        clickOne()
        assertEquals("-1", state.value.input)

        clickMinus()
        assertEquals("-1-", state.value.input)

        clickTwo()
        assertEquals("-1-2", state.value.input)

        clickEquals()
        assertEquals("-1-2", state.value.input)
        assertEquals("-3", state.value.result)
    }

    @Test
    fun prevent_multiple_zeros_minus_operation() = with(viewModel) {
        repeat(3) {
            clickZero()
            assertEquals("0", state.value.input)
        }

        clickMinus()
        assertEquals("0-", state.value.input)

        repeat(3) {
            clickZero()
            assertEquals("0-0", state.value.input)
        }

        clickEquals()
        assertEquals("0-0", state.value.input)
        assertEquals("0", state.value.result)
    }
}

private class FakeCalculator(
    private val calculator: Calculator = Calculator.Base(),
) : Calculator {

    private var plusCalledCount = 0
    private var minusCalledCount = 0

    override fun plus(left: String, right: String): String =
        calculator.plus(left, right).also { plusCalledCount++ }

    override fun minus(left: String, right: String): String =
        calculator.minus(left, right).also { minusCalledCount++ }

    fun assertPlusCalled(expectedTimes: Int) =
        assertEquals(expectedTimes, plusCalledCount)

    fun assertMinusCalled(expectedTimes: Int) =
        assertEquals(expectedTimes, minusCalledCount)
}
