package com.jaehl.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehl.spacex.R
import com.jaehl.spacex.data.repository.LaunchesRepository
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.ui.JobDispatcher
import com.jaehl.spacex.ui.extensions.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

import com.jaehl.spacex.ui.extensions.launchDataLoad
import timber.log.Timber

@HiltViewModel
class LaunchListViewModel @Inject constructor(private val repository: LaunchesRepository, private val dispatcher: JobDispatcher) : ViewModel() {

    private val _onError = ActionLiveData<Int>()
    val onError: LiveData<Int> = _onError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _items = MutableLiveData<List<LaunchViewData>>()
    val items: LiveData<List<LaunchViewData>> = _items

    init {
        update()
    }

    private fun update(){

        launchDataLoad(dispatcher, doOnError = {passError(it)} ){
            repository.getLaunches().collect{ result ->
                when(result.status){
                    Result.Status.SUCCESS -> {
                        _items.postValue(passListResult(result.data))
                        _loading.postValue(false)
                    }
                    Result.Status.ERROR -> {
                        Timber.e(result.error,"LaunchListViewModel")
                        _onError.postValue(result.message ?: R.string.generic_error)
                        _loading.postValue(false)
                    }
                    Result.Status.LOADING -> {
                        _items.postValue(passListResult(result.data))
                        _loading.postValue(true)
                    }
                }
            }
        }
    }

    private fun passError(t : Throwable){
        Timber.e(t, "LaunchListViewModel")
        _onError.postValue(R.string.generic_error)
    }

    private fun passListResult(list : List<Launch>?) : List<LaunchViewData>{
        val data = list ?: arrayListOf()
        return data.filter { !it.upcoming }
            .sortedByDescending { it.dateUnix }
            .map { launchModelToViewData(it) }
    }

    private fun launchModelToViewData(launch : Launch) : LaunchViewData {
        return LaunchViewData(launch.id, launch.name ?: "missing", launch.links?.patch?.small ?: "")
    }
}