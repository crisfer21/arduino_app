package com.example.apparduino3.ui.history

import com.example.apparduino3.data.HistoryEntry
import com.example.apparduino3.data.HistoryRepository
import com.google.firebase.Timestamp
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var historyViewModel: HistoryViewModel
    private val historyRepository: HistoryRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        historyViewModel = HistoryViewModel(historyRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getHistory success`() = runTest {
        // Given
        val historyList = listOf(HistoryEntry(Timestamp.now(), "Available"))
        coEvery { historyRepository.getHistory() } returns historyList

        // When
        historyViewModel.getHistory()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assert(historyViewModel.history.value == historyList)
    }
}
