package it.facile.records.agent.domain.usecase

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordBusinessData
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RecordsListUsecaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockSuccesResultList = listOf<RecordBusinessData>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { recordName } returns "Record1"
        },
        mockk(relaxed = true) {
            every { id } returns 2
            every { recordName } returns "Record2"
        },
        mockk(relaxed = true) {
            every { id } returns 3
            every { recordName } returns "Record3"
        })

    private val mockErrorResult: Result<List<RecordBusinessData>> = Result.Error(Exception("error"))

    private lateinit var useCase: RecordsListUsecase

    @MockK(relaxed = true)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RecordsListUsecase(repository)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that correct requested is returned as successfull`() {
        coEvery { repository.getAllRecordsFromServer() } returns Result.Success(mockSuccesResultList)

        val retrieveRecords = runBlocking { useCase.retrieveRecords() }

        assertThat(retrieveRecords.succeded).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify simple wrong call correct answer`() {
        coEvery { repository.getAllRecordsFromServer() } returns mockErrorResult

        val retrieveRecords = runBlocking { useCase.retrieveRecords() }

        assertThat(retrieveRecords.failed).isTrue()
    }
}
