package org.wit.bikeshop.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


class BikeShopMemStore : BikeShopStore, AnkoLogger {

    val bikes = ArrayList<BikeShopModel>()

    override fun findAll(): List<BikeShopModel> {
        return bikes
    }


    override fun create(bike: BikeShopModel) {
        bike.id = getId()
        bikes.add(bike)
        logAll()
    }

    override fun update(bike: BikeShopModel) {
        var foundBike: BikeShopModel? = bikes.find { p -> p.id == bike.id }
        if (foundBike != null) {
            foundBike.title = bike.title
            foundBike.size = bike.size
            foundBike.style = bike.style
            foundBike.gender = bike.gender
            foundBike.price = bike.price
            foundBike.condition = bike.condition
            foundBike.comment = bike.comment
            foundBike.image = bike.image
            foundBike.lat = bike.lat
            foundBike.lng = bike.lng
            foundBike.zoom = bike.zoom
            logAll()
        }
    }

    fun logAll() {
        bikes.forEach { info("${it}") }
    }

    override fun delete(bike: BikeShopModel) {
        bikes.remove(bike)
    }

}