package org.wit.bikeshop.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


/* This is a class that implements the BikeShopStore interface and the AnkoLogger interface. It also
creates an arraylist of BikeShopModel objects. */
class BikeShopMemStore : BikeShopStore, AnkoLogger {

    val bikes = ArrayList<BikeShopModel>()

    /**
     * > This function returns a list of BikeShopModel objects
     *
     * @return A list of BikeShopModel objects
     */
    override fun findAll(): List<BikeShopModel> {
        return bikes
    }


    /**
     * It creates a new bike and adds it to the list of bikes.
     *
     * @param bike BikeShopModel - this is the object that is being passed in to the function.
     */
    override fun create(bike: BikeShopModel) {
        bike.id = getId()
        bikes.add(bike)
        logAll()
    }

    /**
     * If the bike is found, then update the bike with the new values
     *
     * @param bike BikeShopModel - this is the bike that is being updated.
     */
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

    /**
     * For each bike in the bikes list, print the bike's name.
     */
    fun logAll() {
        bikes.forEach { info("${it}") }
    }

    /**
     * It removes the bike from the list of bikes.
     *
     * @param bike BikeShopModel - this is the bike that we want to delete from the list
     */
    override fun delete(bike: BikeShopModel) {
        bikes.remove(bike)
    }

}