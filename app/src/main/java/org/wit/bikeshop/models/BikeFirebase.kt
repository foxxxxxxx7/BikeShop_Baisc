package org.wit.bikeshop.models

import org.jetbrains.anko.AnkoLogger

/* > The BikeFirebase class is a Kotlin class that implements the BikeShopStore interface and the
AnkoLogger interface */
class BikeFirebase : BikeShopStore, AnkoLogger {
    override fun findAll(): List<BikeModel> {
        TODO("Not yet implemented")
    }

    override fun create(bike: BikeModel) {
        TODO("Not yet implemented")
    }

    override fun update(bike: BikeModel) {
        TODO("Not yet implemented")
    }

    override fun delete(bike: BikeModel) {
        TODO("Not yet implemented")
    }
}