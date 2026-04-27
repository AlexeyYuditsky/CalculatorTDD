package alexeyyuditsky.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    private val calculator = FakeCalculator()
    private val viewModel = CalculatorViewModel(calculator = calculator)

    @Test
    fun sum_of_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickPlus()
        assertEquals("1+", calculatorState.value.input)

        clickTwo()
        assertEquals("1+2", calculatorState.value.input)

        clickEquals()
        assertEquals("1+2", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)
    }

    @Test
    fun sum_of_numbers_more_complex() = with(viewModel) {
        clickTwo()
        assertEquals("2", calculatorState.value.input)

        clickOne()
        assertEquals("21", calculatorState.value.input)

        clickZero()
        assertEquals("210", calculatorState.value.input)

        clickZero()
        assertEquals("2100", calculatorState.value.input)

        clickPlus()
        assertEquals("2100+", calculatorState.value.input)

        clickOne()
        assertEquals("2100+1", calculatorState.value.input)

        clickTwo()
        assertEquals("2100+12", calculatorState.value.input)

        clickZero()
        assertEquals("2100+120", calculatorState.value.input)

        clickZero()
        assertEquals("2100+1200", calculatorState.value.input)

        clickEquals()
        assertEquals("2100+1200", calculatorState.value.input)
        assertEquals("3300", calculatorState.value.result)
    }

    @Test
    fun sum_of_two_numbers_corner_case() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        var expected = "1"
        repeat(9) {
            clickZero()
            expected += 0
            assertEquals(expected, calculatorState.value.input)
        }
        assertEquals("1000000000", calculatorState.value.input)

        clickPlus()
        assertEquals("1000000000+", calculatorState.value.input)

        clickTwo()
        assertEquals("1000000000+2", calculatorState.value.input)

        expected = "1000000000+2"
        repeat(9) {
            clickZero()
            expected += 0
            assertEquals(expected, calculatorState.value.input)
        }
        assertEquals("1000000000+2000000000", calculatorState.value.input)

        clickEquals()
        assertEquals("1000000000+2000000000", calculatorState.value.input)
        assertEquals("3000000000", calculatorState.value.result)
    }

    @Test
    fun prevent_multiple_zeros_sum_operation() = with(viewModel) {
        repeat(3) {
            clickZero()
            assertEquals("0", calculatorState.value.input)
        }

        clickPlus()
        assertEquals("0+", calculatorState.value.input)

        repeat(3) {
            clickZero()
            assertEquals("0+0", calculatorState.value.input)
        }

        clickEquals()
        assertEquals("0+0", calculatorState.value.input)
        assertEquals("0", calculatorState.value.result)
    }

    @Test
    fun prevent_multiple_zeros_diff_operation() = with(viewModel) {
        repeat(3) {
            clickZero()
            assertEquals("0", calculatorState.value.input)
        }

        clickMinus()
        assertEquals("0-", calculatorState.value.input)

        repeat(3) {
            clickZero()
            assertEquals("0-0", calculatorState.value.input)
        }

        clickEquals()
        assertEquals("0-0", calculatorState.value.input)
        assertEquals("0", calculatorState.value.result)
    }

    @Test
    fun prevent_leading_zeros() = with(viewModel) {
        clickZero()
        assertEquals("0", calculatorState.value.input)

        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickPlus()
        assertEquals("1+", calculatorState.value.input)

        clickZero()
        assertEquals("1+0", calculatorState.value.input)

        clickTwo()
        assertEquals("1+2", calculatorState.value.input)

        clickEquals()
        assertEquals("1+2", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)
    }

    @Test
    fun prevent_minus_zero() = with(viewModel) {
        clickMinus()
        assertEquals("-", calculatorState.value.input)

        clickZero()
        assertEquals("-", calculatorState.value.input)

        clickOne()
        assertEquals("-1", calculatorState.value.input)

        clickPlus()
        assertEquals("-1+", calculatorState.value.input)

        clickTwo()
        assertEquals("-1+2", calculatorState.value.input)

        clickZero()
        assertEquals("-1+20", calculatorState.value.input)

        clickEquals()
        assertEquals("-1+20", calculatorState.value.input)
        assertEquals("19", calculatorState.value.result)
    }

    @Test
    fun prevent_multiple_plus_operation() = with(viewModel) {
        clickTwo()
        assertEquals("2", calculatorState.value.input)

        repeat(3) {
            clickPlus()
            assertEquals("2+", calculatorState.value.input)
        }

        clickOne()
        assertEquals("2+1", calculatorState.value.input)

        clickEquals()
        assertEquals("2+1", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)
    }

    @Test
    fun prevent_multiple_minus_operation() = with(viewModel) {
        clickTwo()
        assertEquals("2", calculatorState.value.input)

        repeat(3) {
            clickMinus()
            assertEquals("2-", calculatorState.value.input)
        }

        clickOne()
        assertEquals("2-1", calculatorState.value.input)

        clickEquals()
        assertEquals("2-1", calculatorState.value.input)
        assertEquals("1", calculatorState.value.result)
    }

    @Test
    fun prevent_leading_pluses() = with(viewModel) {
        repeat(3) {
            clickPlus()
            assertEquals("", calculatorState.value.input)
        }

        clickTwo()
        assertEquals("2", calculatorState.value.input)

        clickPlus()
        assertEquals("2+", calculatorState.value.input)

        clickOne()
        assertEquals("2+1", calculatorState.value.input)

        clickEquals()
        assertEquals("2+1", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)
    }

    @Test
    fun sum_of_more_than_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickPlus()
        assertEquals("1+", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickTwo()
        assertEquals("1+2", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        repeat(3) {
            clickPlus()
            assertEquals("3+", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
            calculator.assertSumCalled(expectedTimes = 1)
        }

        clickOne()
        assertEquals("3+1", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickZero()
        assertEquals("3+10", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        repeat(3) {
            clickPlus()
            assertEquals("13+", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
            calculator.assertSumCalled(expectedTimes = 2)
        }

        clickTwo()
        assertEquals("13+2", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        repeat(3) {
            clickEquals()
            assertEquals("13+2", calculatorState.value.input)
            assertEquals("15", calculatorState.value.result)
            calculator.assertSumCalled(expectedTimes = 3)
        }
    }

    @Test
    fun diff_of_more_than_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickMinus()
        assertEquals("1-", calculatorState.value.input)

        clickTwo()
        assertEquals("1-2", calculatorState.value.input)

        repeat(3) {
            clickMinus()
            assertEquals("-1-", calculatorState.value.input)
            calculator.assertDiffCalled(expectedTimes = 1)
        }

        clickTwo()
        assertEquals("-1-2", calculatorState.value.input)

        clickZero()
        assertEquals("-1-20", calculatorState.value.input)

        repeat(3) {
            clickMinus()
            assertEquals("-21-", calculatorState.value.input)
            calculator.assertDiffCalled(expectedTimes = 2)
        }

        clickTwo()
        assertEquals("-21-2", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("-21-2", calculatorState.value.input)
            assertEquals("-23", calculatorState.value.result)
            calculator.assertDiffCalled(expectedTimes = 3)
        }
    }

    @Test
    fun sum_after_equals() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickPlus()
        assertEquals("1+", calculatorState.value.input)

        clickTwo()
        assertEquals("1+2", calculatorState.value.input)

        clickEquals()
        assertEquals("1+2", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)

        clickPlus()
        assertEquals("3+", calculatorState.value.input)

        clickOne()
        assertEquals("3+1", calculatorState.value.input)

        clickEquals()
        assertEquals("3+1", calculatorState.value.input)
        assertEquals("4", calculatorState.value.result)

        clickPlus()
        assertEquals("4+", calculatorState.value.input)

        clickTwo()
        assertEquals("4+2", calculatorState.value.input)

        clickPlus()
        assertEquals("6+", calculatorState.value.input)

        clickOne()
        assertEquals("6+1", calculatorState.value.input)

        clickEquals()
        assertEquals("6+1", calculatorState.value.input)
        assertEquals("7", calculatorState.value.result)
    }

    @Test
    fun diff_after_equals() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickMinus()
        assertEquals("1-", calculatorState.value.input)

        clickTwo()
        assertEquals("1-2", calculatorState.value.input)

        clickEquals()
        assertEquals("1-2", calculatorState.value.input)
        assertEquals("-1", calculatorState.value.result)

        clickMinus()
        assertEquals("-1-", calculatorState.value.input)

        clickOne()
        assertEquals("-1-1", calculatorState.value.input)

        clickEquals()
        assertEquals("-1-1", calculatorState.value.input)
        assertEquals("-2", calculatorState.value.result)

        clickMinus()
        assertEquals("-2-", calculatorState.value.input)

        clickTwo()
        assertEquals("-2-2", calculatorState.value.input)

        clickMinus()
        assertEquals("-4-", calculatorState.value.input)

        clickOne()
        assertEquals("-4-1", calculatorState.value.input)

        clickEquals()
        assertEquals("-4-1", calculatorState.value.input)
        assertEquals("-5", calculatorState.value.result)
    }

    @Test
    fun prevent_equals_not_at_the_end() = with(viewModel) {
        repeat(3) {
            clickEquals()
            assertEquals("", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickTwo()
        assertEquals("2", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickPlus()
        assertEquals("2+", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2+", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickOne()
        assertEquals("2+1", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2+1", calculatorState.value.input)
            assertEquals("3", calculatorState.value.result)
        }
    }

    @Test
    fun prevent_equals_after_minus() = with(viewModel) {
        repeat(3) {
            clickEquals()
            assertEquals("", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickTwo()
        assertEquals("2", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickMinus()
        assertEquals("2-", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2-", calculatorState.value.input)
            assertEquals("", calculatorState.value.result)
        }

        clickOne()
        assertEquals("2-1", calculatorState.value.input)

        repeat(3) {
            clickEquals()
            assertEquals("2-1", calculatorState.value.input)
            assertEquals("1", calculatorState.value.result)
        }
    }

    @Test
    fun diff_of_two_numbers() = with(viewModel) {
        clickOne()
        assertEquals("1", calculatorState.value.input)

        clickMinus()
        assertEquals("1-", calculatorState.value.input)

        clickTwo()
        assertEquals("1-2", calculatorState.value.input)

        clickEquals()
        assertEquals("1-2", calculatorState.value.input)
        assertEquals("-1", calculatorState.value.result)
    }

    @Test
    fun minus_sign_ahead() = with(viewModel) {
        repeat(3) {
            clickMinus()
            assertEquals("-", calculatorState.value.input)
        }

        clickOne()
        assertEquals("-1", calculatorState.value.input)

        clickMinus()
        assertEquals("-1-", calculatorState.value.input)

        clickTwo()
        assertEquals("-1-2", calculatorState.value.input)

        clickEquals()
        assertEquals("-1-2", calculatorState.value.input)
        assertEquals("-3", calculatorState.value.result)
    }

    @Test
    fun change_minus_to_plus(): Unit = with(viewModel) {
        clickMinus()
        assertEquals("-", calculatorState.value.input)

        clickPlus()
        assertEquals("", calculatorState.value.input)

        clickTwo()
        assertEquals("2", calculatorState.value.input)

        clickMinus()
        assertEquals("2-", calculatorState.value.input)

        clickPlus()
        assertEquals("2+", calculatorState.value.input)

        clickOne()
        assertEquals("2+1", calculatorState.value.input)

        clickMinus()
        assertEquals("3-", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickPlus()
        assertEquals("3+", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickZero()
        assertEquals("3+0", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)

        clickEquals()
        assertEquals("3+0", calculatorState.value.input)
        assertEquals("3", calculatorState.value.result)
    }

    @Test
    fun clear() = with(viewModel) {
        clickZero()
        assertEquals("0", calculatorState.value.input)

        clickPlus()
        assertEquals("0+", calculatorState.value.input)

        clickTwo()
        assertEquals("0+2", calculatorState.value.input)

        clickEquals()
        assertEquals("0+2", calculatorState.value.input)
        assertEquals("2", calculatorState.value.result)

        clickClear()
        assertEquals("", calculatorState.value.input)
        assertEquals("", calculatorState.value.result)
    }
}

private class FakeCalculator(
    private val calculator: Calculator = DefaultCalculator(),
) : Calculator {

    private var sumCalledCount = 0
    private var diffCalledCount = 0

    override fun sum(left: String, right: String): String {
        sumCalledCount++
        return calculator.sum(left, right)
    }

    override fun diff(left: String, right: String): String {
        diffCalledCount++
        return calculator.diff(left, right)
    }

    fun assertSumCalled(expectedTimes: Int) =
        assertEquals(expectedTimes, sumCalledCount)

    fun assertDiffCalled(expectedTimes: Int) =
        assertEquals(expectedTimes, diffCalledCount)
}