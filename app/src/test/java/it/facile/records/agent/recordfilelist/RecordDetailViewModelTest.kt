package it.facile.records.agent.recordfilelist

import InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockKAnnotations
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
import org.junit.Before
import org.junit.Rule
import java.util.*

class RecordDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    val testCoroutineScope = MainCoroutineRule()


    @ExperimentalCoroutinesApi
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


}