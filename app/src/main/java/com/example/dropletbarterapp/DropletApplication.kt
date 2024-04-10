package com.example.dropletbarterapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class DropletApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("e0c97a05-cba1-4ff0-bd42-965c1eec11dd")
    }
}