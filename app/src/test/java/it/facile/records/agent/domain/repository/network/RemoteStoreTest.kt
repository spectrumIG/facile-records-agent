package it.facile.records.agent.domain.repository.network


import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.remote.BeerDetailDTO
import it.facile.records.agent.domain.entity.remote.BeersListDTOItem
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
        listOf(
            mockk<BeersListDTOItem>(relaxed = true) {
                every { id } returns 1
                every { description } returns "Beatiful long description"
                every { tagline } returns "Beatiful long tagline"
            })
    private val mockedSuccessDetailResponse =
        listOf(
            mockk<BeerDetailDTO>(relaxed = true) {
                every { id } returns 1
                every { description } returns "Beatiful long description"
                every { tagline } returns "Beatiful long tagline"
            })


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteStore = RemoteStore(restApi)
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network error it returns correctly failed for list of beers`() {
        coEvery { restApi.getAllBeersWithPagination(any(), any()) } returns Response.error(404, "responseBody".toResponseBody())

        val response = runBlocking { remoteStore.getAllBeersList(1, null, null) }
        assertThat(response.failed).isTrue()
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network success it returns correctly success for list of beers`() {
        coEvery { restApi.getAllBeersWithPagination(any(), any()) } returns Response.success(mockedSuccessResponseList)

        val response = runBlocking { remoteStore.getAllBeersList(1, null, null) }
        assertThat(response.succeded).isTrue()
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network error it returns correctly failed for detail`() {
        coEvery { restApi.getDetailOfABeer(any()) } returns Response.error(404, "responseBody".toResponseBody())

        val response = runBlocking { remoteStore.getBeerDetailBy(1) }
        assertThat(response.failed).isTrue()
    }

    @ExperimentalStdlibApi
    @Test
    fun `test on network success it returns correctly success for detail`() {
        coEvery { restApi.getDetailOfABeer(any()) } returns Response.success(mockedSuccessDetailResponse)

        val response = runBlocking { remoteStore.getBeerDetailBy(1) }
        assertThat(response.succeded).isTrue()
    }
}