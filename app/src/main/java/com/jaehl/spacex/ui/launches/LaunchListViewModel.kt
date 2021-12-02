package com.jaehl.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehl.spacex.R
import com.jaehl.spacex.data.repository.LaunchesRepository
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.ui.DataFormatters
import com.jaehl.spacex.ui.JobDispatcher
import com.jaehl.spacex.ui.extensions.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

import com.jaehl.spacex.ui.extensions.launchDataLoad
import timber.log.Timber
import java.util.*

@HiltViewModel
class LaunchListViewModel @Inject constructor(private val repository: LaunchesRepository, private val dispatcher: JobDispatcher) : ViewModel() {

    private val _onError = ActionLiveData<Int>()
    val onError: LiveData<Int> = _onError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _items = MutableLiveData<List<LaunchItemViewData>>()
    val items: LiveData<List<LaunchItemViewData>> = _items

    init {
        update()
    }

    private fun update(){

        launchDataLoad(dispatcher, doOnError = {passError(it)} ){
            repository.getLaunches().collect{ result ->
                when(result){
                    is Result.Success -> {
                        _items.postValue(passListResult(result.data))
                        _loading.postValue(false)
                    }
                    is Result.Error -> {
                        Timber.e(result.error,"LaunchListViewModel")
                        handleError(result)
                        _loading.postValue(false)
                    }
                    is Result.Loading -> {
                        result.data?.let {
                            _items.postValue(passListResult(it))
                        }
                        _loading.postValue(true)
                    }
                }
            }
        }
    }

    private fun handleError(error : Result.Error<*>){
        when(error){
            is Result.Error.NoInternetError -> {
                _onError.postValue( R.string.no_internet_error)
            }
            else -> {
                _onError.postValue( R.string.generic_error)
            }
        }
    }

    private fun passError(t : Throwable){
        Timber.e(t, "LaunchListViewModel")
        _onError.postValue(R.string.generic_error)
    }

    private fun passListResult(list : List<Launch>) : List<LaunchItemViewData>{
        return list.filter { !it.upcoming }
            .sortedByDescending { it.dateUnix }
            .map { launchModelToViewData(it) }
    }

    private fun launchModelToViewData(launch : Launch) : LaunchItemViewData {
        return LaunchItemViewData(
            launch.id,
            launch.name ?: "missing",
            launch.links?.patch?.small ?: "",
            parseDate(launch.dateUnix),
            parseStatus(launch)
        )
    }

    private fun parseDate(dateUnix : Long?) : String{
        if(dateUnix == null) return ""
        return DataFormatters.dayDateFormatter.format(Date(dateUnix*1000L))
    }

    private fun parseStatus(launch : Launch) : Int{
        return when{
            launch.upcoming -> {
                R.string.launch_status_upcoming
            }
            launch.success -> {
                R.string.launch_status_success
            }
            else ->{
                R.string.launch_status_failure
            }
        }
    }
}