package it.facile.records.agent.domain.repository

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordBusinessData
import it.facile.records.agent.domain.entity.local.RecordDataModel
import it.facile.records.agent.domain.entity.remote.RecordListDTO.RecordDTO
import it.facile.records.agent.domain.repository.database.LocalDataStoreImpl
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    private lateinit var localStore: LocalDataStoreImpl

    private lateinit var repository: Repository

//    private val mockSuccesResultList = listOf<RecordBusinessData>(
//        mockk(relaxed = true) {
//            every { id } returns 1
//            every { recordName } returns "Record1"
//        },
//        mockk(relaxed = true) {
//            every { id } returns 2
//            every { recordName } returns "Record2"
//        },
//        mockk(relaxed = true) {
//            every { id } returns 3
//            every { recordName } returns "Record3"
//        })

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
    fun `verify list is returned correctly when is correct`() {
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
        coEvery { remoteStore.getAllrecords() } returns  Result.Error(Exception("test"))
        val result = runBlocking { repository.getAllRecordsFromServer() }

        assertThat(result.failed).isTrue()

        val error = result as Result.Error
        assertThat(error.exception).isInstanceOf(Exception::class.java)
    }

}