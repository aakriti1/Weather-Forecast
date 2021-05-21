package com.aakriti.assignment

import android.app.Application
import com.aakriti.assignment.utils.SharedPrefsManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefsManager.init(this)
    }
}