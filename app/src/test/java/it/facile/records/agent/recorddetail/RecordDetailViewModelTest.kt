package it.facile.records.agent.recorddetail

import InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.facile.records.agent.domain.entity.local.RecordDetailUI
import it.facile.records.agent.domain.usecase.RecordDetailUseCase
import it.facile.records.agent.library.android.entity.Result
import it.facile.records.agent.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecordDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var mainViewModel: RecordDetailViewModel

    @MockK(relaxed = true)
    private val useCase = mockk<RecordDetailUseCase>()

    private val mockSuccesDetailResultList = listOf<RecordDetailUI>(
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
        mainViewModel = RecordDetailViewModel(useCase)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for detail  page it returns something correct`() {
        mainViewModel.recordDetail.observeForever {}

        coEvery { useCase.retrieveBeerDetailBy(any()) } returns Result.Success(mockSuccesDetailResultList)

        mainViewModel.fetchBeerDetail(1)

        val result = mainViewModel.recordDetail.value
        assertEquals(1,result!!.id)
        assertEquals("Punk IPA", result.name)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that asking for error detail it manage correctly the error`() {
        mainViewModel.recordDetail.observeForever {}

        coEvery { useCase.retrieveBeerDetailBy(any()) } returns Result.Error(Exception("test"))

        mainViewModel.fetchBeerDetail(1)

        val result = mainViewModel.showError.value

        assertEquals(true, result)

    }

}