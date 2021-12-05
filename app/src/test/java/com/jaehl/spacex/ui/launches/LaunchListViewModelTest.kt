package com.jaehl.spacex.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaehl.spacex.CoroutineTestRule
import com.jaehl.spacex.TestDispatcher
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.LaunchLinks
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.repository.LaunchesRepository
import com.jaehl.spacex.util.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.jaehl.spacex.R
import com.jaehl.spacex.data.model.LaunchPatch
import com.jaehl.spacex.ui.DataFormatters
import com.jaehl.spacex.util.TestableObserver
import java.lang.Exception

@ExperimentalCoroutinesApi
class LaunchListViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : LaunchListViewModel

    private val testDispatcher = TestDispatcher()

    @MockK
    var repository: LaunchesRepository = mockk()

    private fun createLaunch(id : String, name : String = "", smallPatch : String = "", date : Long = 0L, upcoming : Boolean = false, success : Boolean = false) : Launch {
        return Launch(
            id = id,
            name = name,
            details = null,
            links = LaunchLinks(
                patch = LaunchPatch(
                    small = smallPatch,
                    large = ""
                ),
                presskit = null,
                webcast = null,
                youtubeId = null,
                article = null,
                wikipedia = null
            ),
            dateUnix = date,
            upcoming = upcoming,
            success = success,
            flightNumber = 1,
            rocket = ""
        )
    }

    @Test
    fun `items test launch model converts to LaunchItemViewData`(){

        val dateString = "01-01-2020"
        val date = DataFormatters.dayDateFormatter.parse(dateString)
        val launch1 = createLaunch(id= "1", name = "name", smallPatch = "http://url", date=(date!!.time/1000), upcoming=false, success=false)

        every {
            repository.getLaunches()
        } returns flow {
            emit(Result.success(arrayListOf(launch1)))
        }

        viewModel = LaunchListViewModel(repository, testDispatcher)

        val items = viewModel.items.getOrAwaitValue()

        val item = items.getOrNull(0)
        assertThat(item).isNotNull()

        assertThat(item?.id).isEqualTo(launch1.id)
        assertThat(item?.name).isEqualTo(launch1.name)
        assertThat(item?.patchUrl).isEqualTo(launch1.links?.patch?.small)
        assertThat(item?.date).isEqualTo(dateString)
        assertThat(item?.statusStringRef).isEqualTo(R.string.launch_status_failure)
    }

    @Test
    fun `loading reacts to repository loading results`() {

        every {
            repository.getLaunches()
        } returns flow {
            emit(Result.loading(arrayListOf(createLaunch("1"))))
            emit(Result.success(arrayListOf(createLaunch("2"))))
        }

        coroutinesTestRule.pauseDispatcher()

        viewModel = LaunchListViewModel(repository, testDispatcher)

        val observer = TestableObserver<Boolean>()
        viewModel.loading.observeForever(observer)

        coroutinesTestRule.resumeDispatcher()

        //matches the loading events, loading then success
        assertThat(observer.getAll()).containsExactly(true, false).inOrder()
    }

    @Test
    fun `onError repository error results`(){

        every {
            repository.getLaunches()
        } returns flow {
            emit(Result.error())
        }

        viewModel = LaunchListViewModel(repository, testDispatcher)

        val onError = viewModel.onError.getOrAwaitValue()
        assertThat(onError).isNotNull()
        assertThat(onError).isEqualTo(R.string.generic_error)
    }

    @Test
    fun `onError repository noInternetError`(){

        every {
            repository.getLaunches()
        } returns flow {
            emit(Result.noInternetError())
        }

        viewModel = LaunchListViewModel(repository, testDispatcher)

        val onError = viewModel.onError.getOrAwaitValue()
        assertThat(onError).isNotNull()
        assertThat(onError).isEqualTo(R.string.no_internet_error)
    }
}