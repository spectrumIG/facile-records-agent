package it.facile.records.agent.domain.repository

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.FileOfRecord
import it.facile.records.agent.domain.entity.local.FileOfRecordBusiness
import it.facile.records.agent.domain.entity.local.RecordBusinessData
import it.facile.records.agent.domain.entity.local.RecordDataModel
import it.facile.records.agent.domain.entity.remote.RecordListDTO.RecordDTO
import it.facile.records.agent.domain.repository.database.LocalStore
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalStdlibApi
class RepositoryTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @MockK
    private lateinit var remoteStore: RemoteStore

    @MockK
    private lateinit var localStore: LocalStore

    private lateinit var repository: Repository

    private val mockRecordFileListSuccess = flow<List<FileOfRecord?>> {
        listOf<FileOfRecordBusiness>(
            mockk(relaxed = true) {
                every { filename } returns "File1"
                every { fileSize } returns 122012
                every { addingDate } returns Calendar.getInstance()
            },
            mockk(relaxed = true) {
                every { filename } returns "File2"
                every { fileSize } returns 1220444
                every { addingDate } returns Calendar.getInstance()
            },
            mockk(relaxed = true) {
                every { filename } returns "File3"
                every { fileSize } returns 1222
                every { addingDate } returns Calendar.getInstance()
            }
        )
    }

    private val mockedSuccessDTOResponseList = listOf<RecordDTO>(

        mockk {
            every { id } returns 1
            every { recordName } returns "Records1"
            every { maptoRecord() } returns RecordDataModel(id, recordName)

        },
        mockk {
            every { id } returns 2
            every { recordName } returns "Records2"
            every { maptoRecord() } returns RecordDataModel(id, recordName)

        },
        mockk {
            every { id } returns 3
            every { recordName } returns "Records23"
            every { maptoRecord() } returns RecordDataModel(id, recordName)
        })

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = RepositoryImpl(localDataStore = localStore, remoteDataStore = remoteStore)

    }

    @Test
    fun `verify records list is returned correctly when is correct`() {
        coEvery { remoteStore.getAllrecords() } returns Result.Success(mockedSuccessDTOResponseList)
        val result = runBlocking { repository.getAllRecordsFromServer() }

        assertThat(result.succeded).isTrue()

        val success = result as Result.Success
        assertThat(success.data[0]).isInstanceOf(RecordBusinessData::class.java)
        assertThat(success.data[0]?.id).isEqualTo(1)
        assertThat(success.data[0]?.recordName).isEqualTo("Records1")
    }

    @Test
    fun `verify error is returned correctly if wrong result`() {
        coEvery { remoteStore.getAllrecords() } returns Result.Error(Exception("test"))
        val result = runBlocking { repository.getAllRecordsFromServer() }

        assertThat(result.failed).isTrue()

        val error = result as Result.Error
        assertThat(error.exception).isInstanceOf(Exception::class.java)
    }

    @Test
    fun `verify file list list is returned correctly by id when is correct`() = runBlocking {
        coEvery { localStore.getFilesForRecordsBy(any()) } returns mockRecordFileListSuccess

        val result = repository.fetchRecordFileListByRecord(1)

        result.collect { list ->

            assertThat(list).isNotEmpty()

            assertThat(list[0]).isInstanceOf(FileOfRecord::class.java)
            assertThat(list[0]?.filename).isEqualTo("File1")
            assertThat(list[0]?.fileSize).isEqualTo(1220444)
        }
    }

}