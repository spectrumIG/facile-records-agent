package it.facile.records.agent.recordfilelist

import InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.FileOfRecordUI
import it.facile.records.agent.domain.usecase.DeleteFileUseCase
import it.facile.records.agent.domain.usecase.InsertFileUseCase
import it.facile.records.agent.domain.usecase.RecordFileListUseCase
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class RecordDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    val testCoroutineScope = MainCoroutineRule()


    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var mainViewModel: RecordDetailViewModel

    @MockK(relaxed = true)
    private val listUseCase = mockk<RecordFileListUseCase>()

    @MockK(relaxed = true)
    private val insertUseCase = mockk<InsertFileUseCase>()

    @MockK(relaxed = true)
    private val deleteUseCase = mockk<DeleteFileUseCase>()

    private val mockSuccesDetailResultList =
        flow<List<FileOfRecordUI>> {
            listOf<FileOfRecordUI>(
                mockk(relaxed = true) {
                    every { filename } returns "File1"
                    every { fileSize } returns 12222
                    every { addingDate } returns Calendar.getInstance()
                },
                mockk(relaxed = true) {
                    every { filename } returns "File2"
                    every { fileSize } returns 2222222
                    every { addingDate } returns Calendar.getInstance()
                },
                mockk(relaxed = true) {
                    every { filename } returns "File3"
                    every { fileSize } returns 124
                    every { addingDate } returns Calendar.getInstance()
                },
            )
        }

    private val mockErrorResultList =
        flow<List<FileOfRecordUI>> { listOf<FileOfRecordUI>() }

    private val captor = argumentCaptor<List<FileOfRecordUI>>()
    private val observer: Observer<List<FileOfRecordUI?>> = mock()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = RecordDetailViewModel(testCoroutineDispatcher, listUseCase, insertUseCase, deleteUseCase)
        mainViewModel.fileForRecord.observeForever(observer)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for file list  page it returns something correct`() = testCoroutineScope.runBlockingTest {
        mainViewModel.fileForRecord.observeForever {}

        coEvery { listUseCase.retrievefilesForRecordBy(1) } returns mockSuccesDetailResultList

        mainViewModel.fetchRecordDetail(1)

//        mainViewModel.fileForRecord.getOrAwaitValue()
//        mainViewModel.fileForRecord.getOrAwaitValue {
//            val empty = mainViewModel.fileForRecord.value
//            coroutineScope.advanceUntilIdle()
//            val success = mainViewModel.fileForRecord.value
//
//            assertThat(success).isNotEmpty()
//            assertThat(success?.get(0)?.filename).isEqualTo("File1")
//            assertThat(success?.get(0)?.fileSize).isEqualTo(12222)
//        }
//        mainViewModel.fetchRecordDetail(1)

        verify(observer, Mockito.times(1)).onChanged(captor.capture())

//        confirmVerified(mainViewModel)
//        mainViewModel.fileForRecord.observeForTesting {
//            val empty = mainViewModel.fileForRecord.value
//            coroutineScope.advanceUntilIdle()
//            val success = mainViewModel.fileForRecord.value
//
//            assertThat(success).isNotEmpty()
//            assertThat(success?.get(0)?.filename).isEqualTo("File1")
//            assertThat(success?.get(0)?.fileSize).isEqualTo(12222)
//        }

//        val result = mainViewModel.fileForRecord.value
//
//        assertThat(result).isNotEmpty()
//        assertThat(result?.get(0)?.filename).isEqualTo("File1")
//        assertThat(result?.get(0)?.fileSize).isEqualTo(12222)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error detail it manage correctly the error`() {
        mainViewModel.fileForRecord.observeForever {}

        coEvery { listUseCase.retrievefilesForRecordBy(any()) } returns mockErrorResultList

        mainViewModel.fetchRecordDetail(1)

        val result = mainViewModel.fileForRecord.value

        assertThat(result).isEmpty()
    }

}