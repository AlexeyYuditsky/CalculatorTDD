package alexeyyuditsky.calculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val calculator = FakeCalculator()
    private val viewModel = CalculatorViewModel(calculator = calculator)
    private val state = viewModel.state

    @Before
    fun setup() {
        assertEquals("", state.value.input)
        assertEquals("", state.value.result)
    }

    @Test
    fun sum_of_two_numbers() {
        viewModel.clickOne()
        assertEquals("1", state.value.input)

        viewModel.clickPlus()
        assertEquals("1+", state.value.input)

        viewModel.clickTwo()
        assertEquals("1+2", state.value.input)

        viewModel.clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun sum_of_numbers_more_complex() {
        viewModel.clickTwo()
        assertEquals("2", state.value.input)

        viewModel.clickOne()
        assertEquals("21", state.value.input)

        viewModel.clickZero()
        assertEquals("210", state.value.input)

        viewModel.clickZero()
        assertEquals("2100", state.value.input)

        viewModel.clickPlus()
        assertEquals("2100+", state.value.input)

        viewModel.clickOne()
        assertEquals("2100+1", state.value.input)

        viewModel.clickTwo()
        assertEquals("2100+12", state.value.input)

        viewModel.clickZero()
        assertEquals("2100+120", state.value.input)

        viewModel.clickZero()
        assertEquals("2100+1200", state.value.input)

        viewModel.clickEquals()
        assertEquals("2100+1200", state.value.input)
        assertEquals("3300", state.value.result)
    }

    @Test
    fun sum_of_two_numbers_corner_case() {
        viewModel.clickOne()
        assertEquals("1", state.value.input)

        var expected = "1"
        repeat(9) {
            viewModel.clickZero()
            expected += 0
            assertEquals(expected, state.value.input)
        }
        assertEquals("1000000000", state.value.input)

        viewModel.clickPlus()
        assertEquals("1000000000+", state.value.input)

        viewModel.clickTwo()
        assertEquals("1000000000+2", state.value.input)

        expected = "1000000000+2"
        repeat(9) {
            viewModel.clickZero()
            expected += 0
            assertEquals(expected, state.value.input)
        }
        assertEquals("1000000000+2000000000", state.value.input)

        viewModel.clickEquals()
        assertEquals("1000000000+2000000000", state.value.input)
        assertEquals("3000000000", state.value.result)
    }

    @Test
    fun prevent_multiple_zeros() {
        repeat(3) {
            viewModel.clickZero()
            assertEquals("0", state.value.input)
        }
        viewModel.clickPlus()
        assertEquals("0+", state.value.input)
        repeat(3) {
            viewModel.clickZero()
            assertEquals("0+0", state.value.input)
        }
        viewModel.clickEquals()
        assertEquals("0+0", state.value.input)
        assertEquals("0", state.value.result)
    }

    @Test
    fun prevent_leading_zeros() {
        viewModel.clickZero()
        assertEquals("0", state.value.input)

        viewModel.clickOne()
        assertEquals("1", state.value.input)

        viewModel.clickPlus()
        assertEquals("1+", state.value.input)

        viewModel.clickZero()
        assertEquals("1+0", state.value.input)

        viewModel.clickTwo()
        assertEquals("1+2", state.value.input)

        viewModel.clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun prevent_multiple_plus_operation() {
        viewModel.clickTwo()
        assertEquals("2", state.value.input)

        repeat(3) {
            viewModel.clickPlus()
            assertEquals("2+", state.value.input)
        }

        viewModel.clickOne()
        assertEquals("2+1", state.value.input)

        viewModel.clickEquals()
        assertEquals("2+1", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun prevent_leading_pluses() {
        repeat(3) {
            viewModel.clickPlus()
            assertEquals("", state.value.input)
        }

        viewModel.clickTwo()
        assertEquals("2", state.value.input)

        viewModel.clickPlus()
        assertEquals("2+", state.value.input)

        viewModel.clickOne()
        assertEquals("2+1", state.value.input)

        viewModel.clickEquals()
        assertEquals("2+1", state.value.input)
        assertEquals("3", state.value.result)
    }

    @Test
    fun sum_of_more_than_two_numbers() {
        viewModel.clickOne()
        assertEquals("1", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickPlus()
        assertEquals("1+", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickTwo()
        assertEquals("1+2", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            viewModel.clickPlus()
            calculator.assertSumCalled(expectedTimes = 1)
            assertEquals("3+", state.value.input)
            assertEquals("", state.value.result)
        }

        viewModel.clickOne()
        assertEquals("3+1", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickZero()
        assertEquals("3+10", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            viewModel.clickPlus()
            calculator.assertSumCalled(expectedTimes = 2)
            assertEquals("13+", state.value.input)
            assertEquals("", state.value.result)
        }

        viewModel.clickTwo()
        assertEquals("13+2", state.value.input)
        assertEquals("", state.value.result)

        repeat(3) {
            viewModel.clickEquals()
            calculator.assertSumCalled(expectedTimes = 3)
            assertEquals("13+2", state.value.input)
            assertEquals("15", state.value.result)
        }
    }

    @Test
    fun sum_after_equals() {
        viewModel.clickOne()
        assertEquals("1", state.value.input)

        viewModel.clickPlus()
        assertEquals("1+", state.value.input)

        viewModel.clickTwo()
        assertEquals("1+2", state.value.input)

        viewModel.clickEquals()
        assertEquals("1+2", state.value.input)
        assertEquals("3", state.value.result)

        viewModel.clickPlus()
        assertEquals("3+", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickOne()
        assertEquals("3+1", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickEquals()
        assertEquals("3+1", state.value.input)
        assertEquals("4", state.value.result)

        viewModel.clickPlus()
        assertEquals("4+", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickTwo()
        assertEquals("4+2", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickPlus()
        assertEquals("6+", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickOne()
        assertEquals("6+1", state.value.input)
        assertEquals("", state.value.result)

        viewModel.clickEquals()
        assertEquals("6+1", state.value.input)
        assertEquals("7", state.value.result)
    }

    @Test
    fun prevent_equals_not_at_the_end() {
        repeat(3) {
            viewModel.clickEquals()
            assertEquals("", state.value.input)
            assertEquals("", state.value.result)
        }

        viewModel.clickTwo()
        assertEquals("2", state.value.input)

        repeat(3) {
            viewModel.clickEquals()
            assertEquals("2", state.value.input)
            assertEquals("", state.value.result)
        }

        viewModel.clickPlus()
        assertEquals("2+", state.value.input)

        repeat(3) {
            viewModel.clickEquals()
            assertEquals("2+", state.value.input)
            assertEquals("", state.value.result)
        }

        viewModel.clickOne()
        assertEquals("2+1", state.value.input)

        repeat(3) {
            viewModel.clickEquals()
            assertEquals("2+1", state.value.input)
            assertEquals("3", state.value.result)
        }
        calculator.assertSumCalled(expectedTimes = 1)
    }

    private class FakeCalculator(
        private val calculator: Calculator = Calculator.Base(),
    ) : Calculator {

        private var count = 0

        override fun sum(left: String, right: String): String = calculator.sum(left, right).also { count++ }

        fun assertSumCalled(expectedTimes: Int) = assertEquals(expectedTimes, count)
    }
}