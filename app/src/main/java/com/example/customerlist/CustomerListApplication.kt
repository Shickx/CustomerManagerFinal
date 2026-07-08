package com.example.customerlist

import android.app.Application
import com.example.customerlist.di.AppContainer

class CustomerListApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
