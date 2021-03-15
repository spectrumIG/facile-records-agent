package it.facile.records.agent.domain.usecase

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordDetail
import it.facile.records.agent.domain.entity.local.SimpleBeer
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

    private val mockSuccesResultList = listOf<SimpleBeer>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { name } returns "Punk IPA"
            every { date } returns "12-2012"
            every { tagline } returns "Beatiful long tagline"
        },
        mockk(relaxed = true) {
            every { id } returns 2
            every { name } returns "Punk IPA"
            every { date } returns "12-2019"
            every { tagline } returns "Beatiful long tagline"
        },
        mockk(relaxed = true) {
            every { id } returns 3
            every { name } returns "Punk IPA"
            every { date } returns "12-2010"
            every { tagline } returns "Beatiful long tagline"
        })

    private val mockSuccessBeerDetail = listOf<RecordDetail>(mockk(relaxed = true) {
        every { id } returns 1
        every { name } returns "Punk IPA"
        every { firstBrewed } returns "12-2012"
        every { tagline } returns "Beatiful long tagline"
    })

    private val mockErrorResult: Result<List<SimpleBeer>> = Result.Error(Exception("error"))

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
    fun `verify that one page requested one page is returned as successfull`() {
        coEvery { repository.getAllRecords(1) } returns Result.Success(mockSuccesResultList)

        val retrieveBeersPaginated = runBlocking() { useCase.retrieveBeersPaginated(1) }

        assertThat(retrieveBeersPaginated.succeded).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify simple paginated wrong call correct answer`() {
        coEvery { repository.getAllRecords(any()) } returns mockErrorResult

        val retrieveBeersPaginated = runBlocking { useCase.retrieveBeersPaginated(1) }

        assertThat(retrieveBeersPaginated.failed).isTrue()
    }


//    @ExperimentalCoroutinesApi
//    @Test
//    fun `verify that one beer detail requested one page is returned as successfull`() {
//        coEvery { repository.fetchBeerDetailBy(1) } returns Result.Success(mockSuccessBeerDetail)
//
//        val retrieveBeersPaginated = runBlocking { useCase.(1) }
//
//        Assert.assertThat(retrieveBeersPaginated.succeded, `is`(true))
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `verify simple paginated wrong call correct answer`() {
//        coEvery { repository.fetchAllBeerPaginated(any()) } returns mockErrorResult
//
//        val retrieveBeersPaginated = runBlocking { useCase.retrieveBeersPaginated(1) }
//
//        Assert.assertThat(retrieveBeersPaginated.failed, `is`(true))
//    }

}
