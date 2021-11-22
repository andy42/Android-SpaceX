package com.jaehl.spacex.data.local.configuration

import android.content.Context
import com.jaehl.spacex.R

class ConfigurationImp(private val  appContext: Context) : Configuration{

    override fun getSpacexApiUrl(): String = appContext.resources.getString(R.string.spacex_api_url)
    override fun getRequestTimeout(): Int = appContext.resources.getInteger(R.integer.request_timeout)
}