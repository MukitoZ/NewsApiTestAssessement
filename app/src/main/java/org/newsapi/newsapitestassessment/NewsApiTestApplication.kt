package org.newsapi.newsapitestassessment

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApiTestApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("GlobalError", e.message.orEmpty(), e.cause)
        }
    }
}