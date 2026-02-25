package alexeyyuditsky.calculator

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val calculatorPage = CalculatorPage(composeTestRule)

    @Test
    fun sum_of_two_numbers() = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        clickPlus()
        assertInput(expected = "1+")

        clickTwo()
        assertInput(expected = "1+2")

        clickEquals()
        assertInput(expected = "1+2")
        assertResult(expected = "3")
    }

    @Test
    fun sum_of_two_numbers_corner_case() = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        var expected = "1"
        repeat(9) {
            clickZero()
            expected += 0
            assertInput(expected = expected)
        }
        assertInput(expected = "1000000000")

        clickPlus()
        assertInput(expected = "1000000000+")

        clickTwo()
        assertInput(expected = "1000000000+2")

        expected = "1000000000+2"
        repeat(9) {
            clickZero()
            expected += 0
            assertInput(expected = expected)
        }
        assertInput(expected = "1000000000+2000000000")

        clickEquals()
        assertInput(expected = "1000000000+2000000000")
        assertResult(expected = "3000000000")
    }

    @Test
    fun prevent_multiple_zeros() = with(calculatorPage) {
        repeat(10) {
            clickZero()
            assertInput(expected = "0")
        }

        clickPlus()
        assertInput(expected = "0+")

        repeat(10) {
            clickZero()
            assertInput(expected = "0+0")
        }

        clickEquals()
        assertInput(expected = "0+0")
        assertResult(expected = "0")
    }

    @Test
    fun prevent_leading_zeros() = with(calculatorPage) {
        clickZero()
        assertInput(expected = "0")

        clickOne()
        assertInput(expected = "1")

        clickPlus()
        assertInput(expected = "1+")

        clickZero()
        assertInput(expected = "1+0")

        clickTwo()
        assertInput(expected = "1+2")

        clickEquals()
        assertInput(expected = "1+2")
        assertResult(expected = "3")
    }
}