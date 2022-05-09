package org.wit.bikeshop.models

/* This is a class that implements the BikeShopStore interface. */
interface BikeShopStore {
    fun findAll(): List<BikeShopModel>
    fun create(bike: BikeShopModel)
    fun update(bike: BikeShopModel)
    fun delete(bike: BikeShopModel)
}