package org.wit.bikeshop.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.bikeshop.models.BikeShopJSONStore
import org.wit.bikeshop.models.BikeShopMemStore
import org.wit.bikeshop.models.BikeShopModel
import org.wit.bikeshop.models.BikeShopStore

/* This is the main application class. It is a subclass of the Android Application class. It also
implements the AnkoLogger interface. This allows us to use the AnkoLogger methods in our
application. */
class MainApp : Application(), AnkoLogger {

    lateinit var bikes: BikeShopStore

    /**
     * The onCreate() function is called when the application is first created
     */
    override fun onCreate() {
        super.onCreate()
        //bikes = BikeShopMemStore()
        bikes = BikeShopJSONStore(applicationContext)
        info("BikeShop started")
    }
}