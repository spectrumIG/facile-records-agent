package it.facile.records.agent.domain.usecase

import InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordDetail
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
class RecordDetailUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockSuccessBeerDetail = listOf<RecordDetail>(mockk(relaxed = true) {
        every { id } returns 1
        every { name } returns "Punk IPA"
        every { firstBrewed } returns "12-2012"
        every { tagline } returns "Beatiful long tagline"
    })

    private val mockErrorResult: Result<List<RecordDetail>> = Result.Error(Exception("error"))

    private lateinit var useCase: RecordDetailUseCase

    @MockK(relaxed = true)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RecordDetailUseCase(repository)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that one beer detail requested one page is returned as successfull and data are correct`() {
        coEvery { repository.fetchRecordDetailBy(1) } returns Result.Success(mockSuccessBeerDetail)

        val retrieveBeersPaginated = runBlocking { useCase.retrieveBeerDetailBy(1) }

        assertThat(retrieveBeersPaginated.succeded).isTrue()
        val beerDetailUI = (retrieveBeersPaginated as Result.Success).data[0]
        assertThat(beerDetailUI.id).isEqualTo(1)
        assertThat(beerDetailUI.name).isEqualTo("Punk IPA")

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify simple paginated wrong call correct answer`() {
        coEvery { repository.fetchRecordDetailBy(any()) } returns mockErrorResult

        val retrieveBeersPaginated = runBlocking { useCase.retrieveBeerDetailBy(1) }

        assertThat(retrieveBeersPaginated.failed).isTrue()
    }

}