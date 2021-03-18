package it.facile.records.agent.recordslist

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordForUi
import it.facile.records.agent.domain.usecase.RecordsListUsecase
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecordsListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var mainViewModel: RecordsListViewModel

    @MockK(relaxed = true)
    private val useCase = mockk<RecordsListUsecase>()

    private val mockSuccesResultList = listOf<RecordForUi>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { recordName } returns "Record1"
            every { hasFile } returns true
        },
        mockk(relaxed = true) {
            every { id } returns 2
            every { recordName } returns "Record2"
            every { hasFile } returns false
        },
        mockk(relaxed = true) {
            every { id } returns 3
            every { recordName } returns "Record3"
            every { hasFile } returns true
        })

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = RecordsListViewModel(useCase)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for first page it returns something correctly`() {
        mainViewModel.records.observeForever {}

        coEvery { useCase.retrieveRecords() } returns Result.Success(mockSuccesResultList)

        mainViewModel.getAllRecords()

        val result = mainViewModel.records.value

        assertThat(result?.succeded).isTrue()

        val successResult = result as Result.Success
        val recordForUi = successResult.data[0]

        assertThat(recordForUi).isInstanceOf(RecordForUi::class.java)
        assertThat(recordForUi.id).isEqualTo(1)
        assertThat(recordForUi.recordName).isEqualTo("Record1")


    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error page it manage correctly the error`() {
        mainViewModel.records.observeForever {}

        coEvery { useCase.retrieveRecords() } returns Result.Error(Exception("test"))

        mainViewModel.getAllRecords()

        val result = mainViewModel.records.value

        assertThat(result?.failed).isTrue()

        val error = result as Result.Error
        assertThat(error.exception.message).isEqualTo("test")

    }

}