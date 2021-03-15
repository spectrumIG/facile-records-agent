package it.facile.records.agent.recordslist

import InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.BeerForUi
import it.facile.records.agent.domain.usecase.RecordsListUsecase
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecordsListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var mainViewModel: RecordsListViewModel

    @MockK(relaxed = true)
    private val useCase = mockk<RecordsListUsecase>()

    private val mockSuccesResultList = listOf<BeerForUi>(
        mockk(relaxed = true) {
            every { id } returns 1
            every { date } returns "12-2012"
            every { tagline } returns "Beatiful long tagline"
            every { name } returns "Punk IPA"
        },
        mockk(relaxed = true) {
            every { id } returns 2
            every { date } returns "12-2019"
            every { tagline } returns "Beatiful long tagline"
            every { name } returns "Punk IPA"
        },
        mockk(relaxed = true) {
            every { id } returns 3
            every { date } returns "12-2010"
            every { tagline } returns "Beatiful long tagline"
            every { name } returns "Punk IPA"
        })

    private val mockSuccesFilteredResultList = listOf<BeerForUi>(
        mockk(relaxed = true) {
            every { id } returns 3
            every { date } returns "12-2010"
            every { tagline } returns "Beatiful long tagline"
            every { name } returns "Punk IPA"
        })

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = RecordsListViewModel(useCase)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for first page it returns something correctly`() {
        mainViewModel.beers.observeForever {}

        coEvery { useCase.retrieveBeersPaginated(any()) } returns Result.Success(mockSuccesResultList)

        mainViewModel.fetchBeersPaginatedWith(1)

        val result = mainViewModel.beers.value
        assertEquals(true, result!!.succeded)
        val successResult = result as Result.Success
        assertEquals(1, successResult.data[0].id)
        assertEquals("Punk IPA", successResult.data[0].name)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error page it manage correctly the error`() {
        mainViewModel.beers.observeForever {}

        coEvery { useCase.retrieveBeersPaginated(any()) } returns Result.Error(Exception("test"))

        mainViewModel.fetchBeersPaginatedWith(1)

        val result = mainViewModel.beers.value

        assertEquals(true, result!!.failed)

        val error = result as Result.Error
        assertEquals("test", error.exception.message)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for filtered page it returns something correctly`() {
        mainViewModel.beers.observeForever {}

        coEvery { useCase.retrieveBeersPaginatedFor(any(), any(), any()) } returns Result.Success(mockSuccesFilteredResultList)

        mainViewModel.fetchBeersPaginatedAndWithFilterDate(1, any(), any())

        val result = mainViewModel.beers.value

        assertEquals(true, result!!.succeded)
        val successResult = result as Result.Success

        assertEquals(3, successResult.data[0].id)
        assertEquals("Punk IPA", successResult.data[0].name)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error page it manage correctly the error even filtered`() {
        mainViewModel.beers.observeForever {}

        coEvery { useCase.retrieveBeersPaginatedFor(any(), any(), any()) } returns Result.Error(Exception("test"))

        mainViewModel.fetchBeersPaginatedAndWithFilterDate(1, any(), any())

        val result = mainViewModel.beers.value

        assertEquals(true, result!!.failed)

        val error = result as Result.Error
        assertEquals("test", error.exception.message)

    }

}