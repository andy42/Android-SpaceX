package com.jaehl.spacex.util

import androidx.lifecycle.Observer

class TestableObserver<T> : Observer<T> {
    private val history: MutableList<T> = mutableListOf()

    override fun onChanged(value: T) {
        history.add(value)
    }

    fun getAll() : List<T> = history.toList()
}