package alexeyyuditsky.calculator

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performClick

class CalculatorPage(rule: ComposeContentTestRule) {

    private val zero = rule.onNode(
        hasTestTag("zero") and hasText("0") and hasClickAction()
    )
    private val one = rule.onNode(
        hasTestTag("one") and hasText("1") and hasClickAction()
    )
    private val two = rule.onNode(
        hasTestTag("two") and hasText("2") and hasClickAction()
    )
    private val plus = rule.onNode(
        hasTestTag("plus") and hasText("+") and hasClickAction()
    )
    private val equals = rule.onNode(
        hasTestTag("equals") and hasText("=") and hasClickAction()
    )
    private val input = rule.onNode(
        hasTestTag("input") and hasNoClickAction()
    )
    private val result = rule.onNode(
        hasTestTag("result") and hasNoClickAction()
    )

    fun clickZero() = zero.performClick()

    fun clickOne() = one.performClick()

    fun clickTwo() = two.performClick()

    fun clickPlus() = plus.performClick()

    fun clickEquals() = equals.performClick()

    fun assertInput(expected: String) = input.assertTextEquals(expected)

    fun assertResult(expected: String) = result.assertTextEquals(expected)
}