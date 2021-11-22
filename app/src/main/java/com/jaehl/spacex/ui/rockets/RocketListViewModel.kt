package com.jaehl.spacex.ui.rockets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaehl.spacex.ui.JobDispatcher
import com.jaehl.spacex.ui.extensions.ActionLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RocketListViewModel @Inject constructor(dispatcher: JobDispatcher) : ViewModel() {

    private val _onError = ActionLiveData<Int>()
    val onError: LiveData<Int> = _onError

    private val _items = MutableLiveData<List<RocketViewData>>()
    val items: LiveData<List<RocketViewData>> = _items

    init {
        //TODO remove later
        _items.postValue(arrayListOf(
            RocketViewData(1, "item 1"),
            RocketViewData(2, "item 2"),
            RocketViewData(3, "item 3"),
            RocketViewData(4, "item 4")

        ))
    }
}