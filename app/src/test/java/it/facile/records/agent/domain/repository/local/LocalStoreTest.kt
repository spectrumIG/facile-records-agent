package it.facile.records.agent.domain.repository.local

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.FileOfRecordBusiness
import it.facile.records.agent.domain.entity.local.RecordFile
import it.facile.records.agent.domain.repository.database.LocalStore
import it.facile.records.agent.domain.repository.database.dao.RecordsDao
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
class LocalStoreTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockRecordFileListSuccess = flow<List<RecordFile>> {
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

    @MockK(relaxed = true)
    private lateinit var recordsDao: RecordsDao

    private lateinit var localStore: LocalStore

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localStore = LocalStore(recordsDao)
    }

    @Test
    fun `verify askin correctly record id it returns something correctly`() = runBlocking {
        coEvery { recordsDao.getAllFileForRecordById(any()) } returns mockRecordFileListSuccess

        val flow = localStore.getFilesForRecordsBy(any())
        flow.collect { list ->

            assertThat(list).isNotEmpty()
            assertThat(list[0]).isInstanceOf(RecordFile::class.java)
            assertThat(list[0]?.fileSize).isEqualTo(122012)
            assertThat(list[0]?.filename).isEqualTo("File1")
        }
    }


}