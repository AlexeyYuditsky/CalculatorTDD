package alexeyyuditsky.calculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val viewModel = CalculatorViewModel()
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
        repeat(10) {
            viewModel.clickZero()
            assertEquals("0", state.value.input)
        }
        viewModel.clickPlus()
        assertEquals("0+", state.value.input)
        repeat(10) {
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
}