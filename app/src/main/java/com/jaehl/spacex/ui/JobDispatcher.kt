package com.jaehl.spacex.ui

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class JobDispatcher @Inject constructor() {
    open fun io(): CoroutineDispatcher = Dispatchers.IO
    open fun computation(): CoroutineDispatcher = Dispatchers.Default
    open fun ui(): CoroutineDispatcher = Dispatchers.Main
}