package org.wit.bikeshop.models

interface BikeShopStore {
    fun findAll(): List<BikeShopModel>
    fun create(bike: BikeShopModel)
    fun update(bike: BikeShopModel)
    fun delete(bike: BikeShopModel)
}