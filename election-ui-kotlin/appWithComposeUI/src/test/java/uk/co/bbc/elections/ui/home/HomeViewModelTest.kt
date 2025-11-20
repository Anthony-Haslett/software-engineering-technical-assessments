package uk.co.bbc.elections.ui.home

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import uk.co.bbc.elections.MainCoroutineRule
import uk.co.bbc.elections.api.Result
import uk.co.bbc.elections.api.Results

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `initial UI state is loading`() {
        // When
        val viewModel = HomeViewModel(StubResultsService())

        // Then
        assertTrue(viewModel.uiState.value.loading)
    }

    @Test
    fun `result list in UI state is initially empty`() {
        // When
        val viewModel = HomeViewModel(StubResultsService())

        // Then
        assertEquals(emptyList<ResultUiState>(), viewModel.uiState.value.results)
    }

    @Test
    fun `when service returns a response, state moves to loaded`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults()
        runCurrent()

        // Then
        assertFalse(viewModel.uiState.value.loading)
    }

    @Test
    fun `when service returns a response, ui state contains results`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                false,
                listOf(
                    Result(0, "Party0", 123),
                    Result(1, "Party1", 234)
                )
            )
        )
        runCurrent()

        // Then
        assertEquals(
            listOf(
                ResultUiState("Party0", "0", "123"),
                ResultUiState("Party1", "1", "234")
            ),
            viewModel.uiState.value.results
        )
    }

    @Test
    fun `when refresh called, move to loading state`() = runTest {
        // Given
        val stubResultsService = StubResultsService()
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults()
        runCurrent()

        assertFalse(viewModel.uiState.value.loading)

        // When
        viewModel.refresh()
        runCurrent()

        // Then
        assertTrue(viewModel.uiState.value.loading)
    }

    @Test
    fun `when counting is not complete, countingComplete is false`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                isComplete = false,
                listOf(
                    Result(0, "Party0", 100),
                    Result(1, "Party1", 200)
                )
            )
        )
        runCurrent()

        // Then
        assertFalse(viewModel.uiState.value.countingComplete)
    }

    @Test
    fun `when counting is complete, countingComplete is true`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                isComplete = true,
                listOf(
                    Result(0, "Party0", 100),
                    Result(1, "Party1", 200)
                )
            )
        )
        runCurrent()

        // Then
        assertTrue(viewModel.uiState.value.countingComplete)
    }

    @Test
    fun `when counting is complete, winner is marked with isWinner flag`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                isComplete = true,
                listOf(
                    Result(0, "Party0", 100),
                    Result(1, "Party1", 200),
                    Result(2, "Party2", 150)
                )
            )
        )
        runCurrent()

        // Then
        val results = viewModel.uiState.value.results
        assertEquals(3, results.size)
        assertFalse(results[0].isWinner)  // Party0 with 100 votes
        assertTrue(results[1].isWinner)   // Party1 with 200 votes (winner)
        assertFalse(results[2].isWinner)  // Party2 with 150 votes
    }

    @Test
    fun `when counting is not complete, no winner is marked`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                isComplete = false,
                listOf(
                    Result(0, "Party0", 100),
                    Result(1, "Party1", 200)
                )
            )
        )
        runCurrent()

        // Then
        val results = viewModel.uiState.value.results
        results.forEach { result ->
            assertFalse(result.isWinner)
        }
    }

    @Test
    fun `when there is a tie, both winners are marked`() = runTest {
        // Given
        val stubResultsService = StubResultsService()

        // When
        val viewModel = HomeViewModel(stubResultsService)
        runCurrent()

        stubResultsService.dispatchResults(
            Results(
                isComplete = true,
                listOf(
                    Result(0, "Party0", 200),
                    Result(1, "Party1", 200),
                    Result(2, "Party2", 100)
                )
            )
        )
        runCurrent()

        // Then
        val results = viewModel.uiState.value.results
        assertEquals(3, results.size)
        assertTrue(results[0].isWinner)   // Party0 with 200 votes (tied winner)
        assertTrue(results[1].isWinner)   // Party1 with 200 votes (tied winner)
        assertFalse(results[2].isWinner)  // Party2 with 100 votes
    }
}
