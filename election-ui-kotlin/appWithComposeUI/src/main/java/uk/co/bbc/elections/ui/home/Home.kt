package uk.co.bbc.elections.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.bbc.elections.R

@Composable
fun Home(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Home(uiState) { viewModel.refresh() }
}

@Composable
fun Home(uiState: HomeUiState, refresh: () -> Unit) = Scaffold(
    floatingActionButton = {
        if (!uiState.countingComplete) {
            FloatingActionButton(
                onClick = { if (!uiState.loading) refresh() },
                modifier = Modifier.safeDrawingPadding()
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(id = R.string.refresh)
                )
            }
        }
    }
) { innerPadding ->
    LazyColumn(
        Modifier
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .safeDrawingPadding()
    ) {
        item {
            Text(
                text = stringResource(
                    id = if (uiState.countingComplete) R.string.counting_complete
                    else R.string.counting_in_progress
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (uiState.countingComplete) MaterialTheme.colors.primary
                        else MaterialTheme.colors.secondary
                    )
                    .padding(16.dp),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (uiState.countingComplete) MaterialTheme.colors.onPrimary
                else MaterialTheme.colors.onSecondary
            )
        }
        item { ResultHeader() }
        items(uiState.results) { Result(it) }
    }
}

@Preview
@Composable
private fun HomePreview() = Home(
    HomeUiState(
        results = listOf(
            ResultUiState("Adder party", "1", "1056"),
            ResultUiState("b", "2", "100")
        ),
        loading = false,
        countingComplete = false
    )
) {}

@Preview
@Composable
private fun HomeCompletePreview() = Home(
    HomeUiState(
        results = listOf(
            ResultUiState("Winner party", "1", "1056", isWinner = true),
            ResultUiState("Second party", "2", "800"),
            ResultUiState("Third party", "3", "500")
        ),
        loading = false,
        countingComplete = true
    )
) {}
