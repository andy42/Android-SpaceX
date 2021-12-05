package com.jaehl.spacex.ui.launchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehl.spacex.R
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.repository.LaunchesRepository
import com.jaehl.spacex.ui.DataFormatters
import com.jaehl.spacex.ui.JobDispatcher
import com.jaehl.spacex.ui.extensions.ActionLiveData
import com.jaehl.spacex.ui.extensions.launchDataLoad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(
    private val repository: LaunchesRepository,
    private val dispatcher: JobDispatcher
) : ViewModel() {

    private val _onError = ActionLiveData<Int>()
    val onError: LiveData<Int> = _onError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _launchDetailsViewData = MutableLiveData<LaunchDetailsViewData>()
    val launchDetailsViewData: LiveData<LaunchDetailsViewData> = _launchDetailsViewData

    private var launchId: String? = null

    fun setLaunchId(id: String) {
        launchId = id
        update()
    }

    private fun update() {

        val launchId = this.launchId ?: return

        launchDataLoad(dispatcher, doOnError = { passError(it) }) {
            repository.getLaunch(launchId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _launchDetailsViewData.postValue(launchModelToViewData(result.data))
                        _loading.postValue(false)
                    }
                    is Result.Error -> {
                        Timber.e(result.error, "LaunchDetailsViewModel")
                        when (result) {
                            is Result.Error.NoInternetError -> {
                                _onError.postValue(R.string.no_internet_error)
                            }
                            else -> {
                                _onError.postValue(R.string.generic_error)
                            }
                        }
                        _loading.postValue(false)
                    }
                    is Result.Loading -> {
                        result.data?.let {
                            _launchDetailsViewData.postValue(launchModelToViewData(it))
                        }
                        _loading.postValue(true)
                    }
                }
            }
        }
    }

    private fun launchModelToViewData(launch: Launch): LaunchDetailsViewData {
        return LaunchDetailsViewData(
            launch.id,
            launch.name ?: "missing",
            launch.links?.patch?.small ?: "",
            parseDate(launch.dateUnix)
        )
    }

    private fun parseDate(dateUnix: Long?): String {
        if (dateUnix == null) return ""
        return DataFormatters.dayDateFormatter.format(Date(dateUnix * 1000L))
    }

    private fun passError(t: Throwable) {
        Timber.e(t, "LaunchListViewModel")
        _onError.postValue(R.string.generic_error)
    }
}