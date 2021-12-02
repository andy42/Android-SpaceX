package com.jaehl.spacex

import com.jaehl.spacex.ui.JobDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcher(private val coroutineDispatcher : CoroutineDispatcher? = null) : JobDispatcher() {
    override fun io(): CoroutineDispatcher = coroutineDispatcher ?: Dispatchers.Unconfined
    override fun computation(): CoroutineDispatcher = coroutineDispatcher ?: Dispatchers.Unconfined
    override fun ui(): CoroutineDispatcher = coroutineDispatcher ?: Dispatchers.Unconfined
}