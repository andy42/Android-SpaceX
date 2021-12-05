package com.jaehl.spacex.ui.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaehl.spacex.ui.JobDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ViewModel.launchDataLoad(
    dispatcher: JobDispatcher,
    doOnError: (Throwable) -> Unit = {},
    doOnComplete: () -> Unit = {},
    block: suspend () -> Unit = {}
): Job = viewModelScope.launch {
    try {
        withContext(dispatcher.io()) {
            block()
        }
    } catch (t: Throwable) {
        doOnError(t)
    } finally {
        doOnComplete()
    }
}

