package uk.co.bbc.elections.ui.home

import uk.co.bbc.elections.api.Candidate
import uk.co.bbc.elections.api.Result

data class HomeViewModelState(
    val results: List<Result> = emptyList(),
    val candidates: List<Candidate> = emptyList(),
    val countingComplete: Boolean = false,
    val loading: Boolean = false
) {

    fun toUiState(): HomeUiState {
        val candidateMap = candidates.associateBy { it.id }
        return HomeUiState(
            results = results.map { result ->
                ResultUiState(
                    result.party,
                    candidateMap[result.candidateId]?.name ?: result.candidateId.toString(),
                    result.votes.toString()
                )
            },
            loading = loading
        )
    }
}
