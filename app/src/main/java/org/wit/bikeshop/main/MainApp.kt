package org.wit.bikeshop.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.bikeshop.models.BikeShopJSONStore
import org.wit.bikeshop.models.BikeShopMemStore
import org.wit.bikeshop.models.BikeShopModel
import org.wit.bikeshop.models.BikeShopStore

class MainApp : Application(), AnkoLogger {

    lateinit var bikes: BikeShopStore

    override fun onCreate() {
        super.onCreate()
        //bikes = BikeShopMemStore()
        bikes = BikeShopJSONStore(applicationContext)
        info("BikeShop started")
    }
}