package it.facile.records.agent.beerdetail

import InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.BeerDetailUI
import it.facile.records.agent.domain.usecase.BeerDetailUseCase
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BeerDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var mainViewModel: BeerDetailViewModel

    @MockK(relaxed = true)
    private val useCase = mockk<BeerDetailUseCase>()

    private val mockSuccesDetailResultList = listOf<BeerDetailUI>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { firstBrewed } returns "12-2012"
            every { tagline } returns "Beatiful long tagline"
            every { name } returns "Punk IPA"
            every { description } returns "Veru very good Punk IPA"
        })


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = BeerDetailViewModel(useCase)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for detail  page it returns something correct`() {
        mainViewModel.beerDetail.observeForever {}

        coEvery { useCase.retrieveBeerDetailBy(any()) } returns Result.Success(mockSuccesDetailResultList)

        mainViewModel.fetchBeerDetail(1)

        val result = mainViewModel.beerDetail.value
        assertEquals(1,result!!.id)
        assertEquals("Punk IPA", result.name)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error detail it manage correctly the error`() {
        mainViewModel.beerDetail.observeForever {}

        coEvery { useCase.retrieveBeerDetailBy(any()) } returns Result.Error(Exception("test"))

        mainViewModel.fetchBeerDetail(1)

        val result = mainViewModel.showError.value

        assertEquals(true, result)

    }

}