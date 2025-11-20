package uk.co.bbc.elections.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Result(result: ResultUiState) = Row(
    modifier = Modifier
        .background(MaterialTheme.colors.surface)
        .padding(8.dp)
        .semantics(mergeDescendants = true) {
            contentDescription = "${result.party}, ${result.id}, ${result.votes} votes"
        }
) {
    Text(
        modifier = Modifier.weight(1f),
        text = result.party,
        color = MaterialTheme.colors.onSurface
    )
    Text(
        modifier = Modifier.weight(1f),
        text = result.id,
        color = MaterialTheme.colors.onSurface
    )
    Text(
        modifier = Modifier.weight(1f),
        text = result.votes,
        color = MaterialTheme.colors.onSurface
    )
}

@Preview
@Composable
private fun ResultPreview() = Result(ResultUiState("Adder party", "1", "1056"))
