package it.facile.records.agent.domain.repository

import InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.SimpleBeer
import it.facile.records.agent.domain.repository.network.RemoteStore
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import junit.framework.Assert.assertEquals
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

    private lateinit var repository: Repository

    private val mockSuccesResultList = listOf<SimpleBeer>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { date } returns "12-2012"
            every { tagline } returns "Beatiful long tagline"
        },
        mockk(relaxed = true) {
            every { id } returns 2
            every { date } returns "12-2019"
            every { tagline } returns "Beatiful long tagline"
        },
        mockk(relaxed = true) {
            every { id } returns 3
            every { date } returns "12-2010"
            every { tagline } returns "Beatiful long tagline"
        })

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = RepositoryImpl(remoteStore)

    }

    @Test
    fun `verify list is returned correctly when input is correct`() {
        coEvery { remoteStore.getAllBeersList(any(),any(),any()) } returns Result.Success(mockSuccesResultList)
        val result = runBlocking { repository.fetchAllBeerPaginated(1) }
        assertEquals(true,result.succeded)

        val success = result as Result.Success
        assertEquals(success.data[0], mockSuccesResultList[0])
    }
    @Test
    fun `verify error is returned correctly if wrong input`() {
        coEvery { remoteStore.getAllBeersList(any(),any(),any()) } throws Exception("test")
        val result = runBlocking { repository.fetchAllBeerPaginated(1) }
        assertEquals(true,result.failed)

        val error = result as Result.Error
        Truth.assertThat(error.exception).isInstanceOf(Exception::class.java)
    }

}