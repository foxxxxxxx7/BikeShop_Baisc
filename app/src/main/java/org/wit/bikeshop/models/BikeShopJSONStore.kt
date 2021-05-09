package org.wit.bikeshop.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.bikeshop.helpers.*
import java.util.*

val JSON_FILE = "bikes.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<BikeShopModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class BikeShopJSONStore : BikeShopStore, AnkoLogger {

    val context: Context
    var bikes = mutableListOf<BikeShopModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BikeShopModel> {
        return bikes
    }

    override fun create(bike: BikeShopModel) {
        bike.id = generateRandomId()
        bikes.add(bike)
        serialize()
    }


    override fun update(bike: BikeShopModel) {
        val bikesList = findAll() as ArrayList<BikeShopModel>
        var foundBike: BikeShopModel? = bikesList.find { p -> p.id == bike.id }
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
        }
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(bikes, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        bikes = Gson().fromJson(jsonString, listType)
    }
    override fun delete(bike: BikeShopModel) {
        bikes.remove(bike)
        serialize()
    }
}