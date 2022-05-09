package org.wit.bikeshop.models

/* This is a class that implements the BikeShopStore interface. */
interface BikeShopStore {
    fun findAll(): List<BikeModel>
    fun create(bike: BikeModel)
    fun update(bike: BikeModel)
    fun delete(bike: BikeModel)
}