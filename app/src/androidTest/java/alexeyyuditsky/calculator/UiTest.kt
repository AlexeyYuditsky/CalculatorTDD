package alexeyyuditsky.calculator

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * N: number (1 - 9)
 * 0: zero
 * M: multiple repetitions
 * MAX: max value
 * C: clear data
 * result: result of operation
 */
@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val calculatorPage = CalculatorPage(composeTestRule)

    // 1. N + N = result
    @Test
    fun sum_of_two_numbers(): Unit = with(calculatorPage) {
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

    // 2. N M + N M = result
    @Test
    fun sum_of_numbers_more_complex(): Unit = with(calculatorPage) {
        clickTwo()
        assertInput(expected = "2")

        clickOne()
        assertInput(expected = "21")

        clickZero()
        assertInput(expected = "210")

        clickZero()
        assertInput(expected = "2100")

        clickPlus()
        assertInput(expected = "2100+")

        clickOne()
        assertInput(expected = "2100+1")

        clickTwo()
        assertInput(expected = "2100+12")

        clickZero()
        assertInput(expected = "2100+120")

        clickZero()
        assertInput(expected = "2100+1200")

        clickEquals()
        assertInput(expected = "2100+1200")
        assertResult(expected = "3300")
    }

    // 3. N MAX + N MAX = result
    @Test
    fun sum_of_two_numbers_corner_case(): Unit = with(calculatorPage) {
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

    // 4. 0 M + 0 M = 0
    @Test
    fun prevent_multiple_zeros_sum_operation(): Unit = with(calculatorPage) {
        repeat(3) {
            clickZero()
            assertInput(expected = "0")
        }

        clickPlus()
        assertInput(expected = "0+")

        repeat(3) {
            clickZero()
            assertInput(expected = "0+0")
        }

        clickEquals()
        assertInput(expected = "0+0")
        assertResult(expected = "0")
    }

    // 5. 0 M - 0 M = 0
    @Test
    fun prevent_multiple_zeros_diff_operation(): Unit = with(calculatorPage) {
        repeat(3) {
            clickZero()
            assertInput(expected = "0")
        }

        clickMinus()
        assertInput(expected = "0-")

        repeat(3) {
            clickZero()
            assertInput(expected = "0-0")
        }

        clickEquals()
        assertInput(expected = "0-0")
        assertResult(expected = "0")
    }

    // 6. 0 N + 0 N = result
    @Test
    fun prevent_leading_zeros(): Unit = with(calculatorPage) {
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

    // 7. - 0 N + N 0 = result
    @Test
    fun prevent_minus_zero(): Unit = with(calculatorPage) {
        clickMinus()
        assertInput(expected = "-")

        clickZero()
        assertInput(expected = "-")

        clickOne()
        assertInput(expected = "-1")

        clickPlus()
        assertInput(expected = "-1+")

        clickTwo()
        assertInput(expected = "-1+2")

        clickZero()
        assertInput(expected = "-1+20")

        clickEquals()
        assertInput(expected = "-1+20")
        assertResult(expected = "19")
    }

    // 8. N + M N = result
    @Test
    fun prevent_multiple_plus_operation(): Unit = with(calculatorPage) {
        clickTwo()
        assertInput(expected = "2")

        repeat(3) {
            clickPlus()
            assertInput(expected = "2+")
        }

        clickOne()
        assertInput(expected = "2+1")

        clickEquals()
        assertInput(expected = "2+1")
        assertResult(expected = "3")
    }

    // 9. N - M N = result
    @Test
    fun prevent_multiple_minus_operation(): Unit = with(calculatorPage) {
        clickTwo()
        assertInput(expected = "2")

        repeat(3) {
            clickMinus()
            assertInput(expected = "2-")
        }

        clickOne()
        assertInput(expected = "2-1")

        clickEquals()
        assertInput(expected = "2-1")
        assertResult(expected = "1")
    }

    // 10. + M N + N = result
    @Test
    fun prevent_leading_pluses(): Unit = with(calculatorPage) {
        repeat(3) {
            clickPlus()
            assertInput(expected = "")
        }

        clickTwo()
        assertInput(expected = "2")

        clickPlus()
        assertInput(expected = "2+")

        clickOne()
        assertInput(expected = "2+1")

        clickEquals()
        assertInput(expected = "2+1")
        assertResult(expected = "3")
    }

    // 11. N + N + N M + N = result
    @Test
    fun sum_of_more_than_two_numbers(): Unit = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")
        assertResult(expected = "")

        clickPlus()
        assertInput(expected = "1+")
        assertResult(expected = "")

        clickTwo()
        assertInput(expected = "1+2")
        assertResult(expected = "")

        repeat(3) {
            clickPlus()
            assertInput(expected = "3+")
            assertResult(expected = "")
        }

        clickOne()
        assertInput(expected = "3+1")
        assertResult(expected = "")

        clickZero()
        assertInput(expected = "3+10")
        assertResult(expected = "")

        repeat(3) {
            clickPlus()
            assertInput(expected = "13+")
            assertResult(expected = "")
        }

        clickTwo()
        assertInput(expected = "13+2")
        assertResult(expected = "")

        repeat(3) {
            clickEquals()
            assertInput(expected = "13+2")
            assertResult(expected = "15")
        }
    }

    // 12. N - N - N M - N = result
    @Test
    fun diff_of_more_than_two_numbers(): Unit = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        clickMinus()
        assertInput(expected = "1-")

        clickTwo()
        assertInput(expected = "1-2")

        clickMinus()
        assertInput(expected = "-1-")

        clickTwo()
        assertInput(expected = "-1-2")

        clickZero()
        assertInput(expected = "-1-20")

        clickMinus()
        assertInput(expected = "-21-")

        clickTwo()
        assertInput(expected = "-21-2")

        clickEquals()
        assertInput(expected = "-21-2")
        assertResult(expected = "-23")
    }

    // 13. N + N = result + N = result + N + N = result
    @Test
    fun sum_after_equals(): Unit = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        clickPlus()
        assertInput(expected = "1+")

        clickTwo()
        assertInput(expected = "1+2")

        clickEquals()
        assertInput(expected = "1+2")
        assertResult(expected = "3")

        clickPlus()
        assertInput(expected = "3+")
        assertResult(expected = "")

        clickOne()
        assertInput(expected = "3+1")
        assertResult(expected = "")

        clickEquals()
        assertInput(expected = "3+1")
        assertResult(expected = "4")

        clickPlus()
        assertInput(expected = "4+")
        assertResult(expected = "")

        clickTwo()
        assertInput(expected = "4+2")
        assertResult(expected = "")

        clickPlus()
        assertInput(expected = "6+")
        assertResult(expected = "")

        clickOne()
        assertInput(expected = "6+1")
        assertResult(expected = "")

        clickEquals()
        assertInput(expected = "6+1")
        assertResult(expected = "7")
    }

    // 14. N - N = result - N = result - N - N = result
    @Test
    fun diff_after_equals(): Unit = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        clickMinus()
        assertInput(expected = "1-")

        clickTwo()
        assertInput(expected = "1-2")

        clickEquals()
        assertInput(expected = "1-2")
        assertResult(expected = "-1")

        clickMinus()
        assertInput(expected = "-1-")

        clickOne()
        assertInput(expected = "-1-1")

        clickEquals()
        assertInput(expected = "-1-1")
        assertResult(expected = "-2")

        clickMinus()
        assertInput(expected = "-2-")

        clickTwo()
        assertInput(expected = "-2-2")

        clickMinus()
        assertInput(expected = "-4-")

        clickOne()
        assertInput(expected = "-4-1")

        clickEquals()
        assertInput(expected = "-4-1")
        assertResult(expected = "-5")
    }

    // 15. = M N = M + = M N = M result
    @Test
    fun prevent_equals_not_at_the_end(): Unit = with(calculatorPage) {
        repeat(3) {
            clickEquals()
            assertInput(expected = "")
            assertResult(expected = "")
        }

        clickTwo()
        assertInput(expected = "2")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2")
            assertResult(expected = "")
        }

        clickPlus()
        assertInput(expected = "2+")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2+")
            assertResult(expected = "")
        }

        clickOne()
        assertInput(expected = "2+1")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2+1")
            assertResult(expected = "3")
        }
    }

    // 16. =M N =M - =M N = M result
    @Test
    fun prevent_equals_after_minus(): Unit = with(calculatorPage) {
        repeat(3) {
            clickEquals()
            assertInput(expected = "")
            assertResult(expected = "")
        }

        clickTwo()
        assertInput(expected = "2")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2")
            assertResult(expected = "")
        }

        clickMinus()
        assertInput(expected = "2-")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2-")
            assertResult(expected = "")
        }

        clickOne()
        assertInput(expected = "2-1")

        repeat(3) {
            clickEquals()
            assertInput(expected = "2-1")
            assertResult(expected = "1")
        }
    }

    // 17. N - N = result
    @Test
    fun diff_of_two_numbers(): Unit = with(calculatorPage) {
        clickOne()
        assertInput(expected = "1")

        clickMinus()
        assertInput(expected = "1-")

        clickTwo()
        assertInput(expected = "1-2")

        clickEquals()
        assertInput(expected = "1-2")
        assertResult(expected = "-1")
    }

    // 18. - N - N = result
    @Test
    fun minus_sign_ahead(): Unit = with(calculatorPage) {
        clickMinus()
        assertInput(expected = "-")

        clickOne()
        assertInput(expected = "-1")

        clickMinus()
        assertInput(expected = "-1-")

        clickTwo()
        assertInput(expected = "-1-2")

        clickEquals()
        assertInput(expected = "-1-2")
        assertResult(expected = "-3")
    }

    // 19. N + N = result C
    @Test
    fun clear(): Unit = with(calculatorPage) {
        clickZero()
        assertInput(expected = "0")

        clickPlus()
        assertInput(expected = "0+")

        clickTwo()
        assertInput(expected = "0+2")

        clickEquals()
        assertInput(expected = "0+2")
        assertResult(expected = "2")

        clickClear()
        assertInput(expected = "")
        assertResult(expected = "")
    }
}