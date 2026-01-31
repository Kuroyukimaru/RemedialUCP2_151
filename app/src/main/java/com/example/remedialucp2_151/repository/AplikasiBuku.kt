package com.example.remedialucp2_151.repository

import android.app.Application

class AplikasiBuku : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}