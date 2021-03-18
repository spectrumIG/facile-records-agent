package it.facile.records.agent.domain.repository.network


import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.remote.RecordListDTO
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RemoteStoreTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var restApi: RestApi

    private lateinit var remoteStore: RemoteStore

    private val mockedSuccessResponseList =
        mockk<RecordListDTO> {
            every { records } returns listOf(
                mockk {
                    every { id } returns 1
                    every { recordName } returns "Records1"
                },
                mockk {
                    every { id } returns 2
                    every { recordName } returns "Records2"
                },
                mockk {
                    every { id } returns 3
                    every { recordName } returns "Records23"
                },
            )
        }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteStore = RemoteStore(restApi)
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network error it returns correctly failed for list of records`() {
        coEvery { restApi.retrieveRecordsFromRemote() } returns Response.error(404, "responseBody".toResponseBody())

        val response = runBlocking {remoteStore.getAllrecords() }

        assertThat(response.succeded).isTrue()
        val success = response as Result.Success
        assertThat(success.data).isEmpty()
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network success it returns correctly success for list of beers`() {
        coEvery { restApi.retrieveRecordsFromRemote() } returns Response.success(mockedSuccessResponseList)

        val response = runBlocking { remoteStore.getAllrecords() }
        assertThat(response.succeded).isTrue()
    }
}