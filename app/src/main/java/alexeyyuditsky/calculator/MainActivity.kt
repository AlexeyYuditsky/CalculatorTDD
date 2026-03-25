package alexeyyuditsky.calculator

import alexeyyuditsky.calculator.ui.theme.CalculatorTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent(content = ::Calculator)
    }
}

@Composable
private fun Calculator() = CalculatorTheme {
    val viewModel = viewModel<CalculatorViewModel>()
    val state by viewModel.calculatorState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        CalculatorScreen(
            calculatorState = state,
            calculatorActions = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}