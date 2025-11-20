package uk.co.bbc.elections.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Result(result: ResultUiState) = Row(
    modifier = Modifier
        .background(
            if (result.isWinner) MaterialTheme.colors.primaryVariant
            else MaterialTheme.colors.surface
        )
        .then(
            if (result.isWinner) Modifier.border(2.dp, MaterialTheme.colors.primary)
            else Modifier
        )
        .padding(8.dp)
) {
    Text(
        modifier = Modifier.weight(1f),
        text = result.party,
        color = if (result.isWinner) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSurface,
        fontWeight = if (result.isWinner) FontWeight.Bold else FontWeight.Normal
    )
    Text(
        modifier = Modifier.weight(1f),
        text = result.id,
        color = if (result.isWinner) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSurface,
        fontWeight = if (result.isWinner) FontWeight.Bold else FontWeight.Normal
    )
    Text(
        modifier = Modifier.weight(1f),
        text = result.votes,
        color = if (result.isWinner) MaterialTheme.colors.onPrimary
        else MaterialTheme.colors.onSurface,
        fontWeight = if (result.isWinner) FontWeight.Bold else FontWeight.Normal
    )
}

@Preview
@Composable
private fun ResultPreview() = Result(ResultUiState("Adder party", "1", "1056"))

@Preview
@Composable
private fun ResultWinnerPreview() = Result(ResultUiState("Winner party", "1", "1056", isWinner = true))
