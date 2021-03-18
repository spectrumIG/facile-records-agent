package it.facile.records.agent.domain.usecase

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.FileOfRecordBusiness
import it.facile.records.agent.domain.repository.Repository
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class RecordFileListUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockSuccessBeerDetail = flow<List<FileOfRecordBusiness?>> {
        listOf<FileOfRecordBusiness>(mockk(relaxed = true) {
            every { filename } returns "File1"
            every { fileSize } returns 122012
            every { addingDate } returns Calendar.getInstance()
        })
    }

//    private val mockErrorResult: Result<List<RecordDetail>> = Result.Error(Exception("error"))

    private lateinit var useCase: RecordFileListUseCase

    @MockK(relaxed = true)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RecordFileListUseCase(repository)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that requesting a legit file list it returns correctly`() = runBlocking {
        coEvery { repository.fetchRecordFileListByRecord(1) } returns mockSuccessBeerDetail

        val flow = useCase.retrievefilesForRecordBy(1)

        flow.collect { value ->
            assertThat(value).isNotEmpty()
            assertThat(value[0]?.filename).isEqualTo("File1")
        }
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `verify simple paginated wrong call correct answer`() {
//        coEvery { repository.fetchRecordDetailBy(any()) } returns mockErrorResult
//
//        val retrieveBeersPaginated = runBlocking { useCase.retrieveBeerDetailBy(1) }
//
//        assertThat(retrieveBeersPaginated.failed).isTrue()
//    }

}