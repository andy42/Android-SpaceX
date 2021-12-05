package com.jaehl.spacex.data.local.configuration

interface Configuration {
    fun getSpacexApiUrl(): String
    fun getRequestTimeout(): Int
}