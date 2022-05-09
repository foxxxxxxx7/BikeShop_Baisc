package org.wit.bikeshop.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.bikeshop.helpers.*
import java.util.*

/* This is declaring the variables that will be used in the class. */
val JSON_FILE = "bikes.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<BikeModel>>() {}.type

/**
 * This function generates a random id.
 *
 * @return A random long value
 */
fun generateRandomId(): Long {
    return Random().nextLong()
}

/* This is declaring the variables that will be used in the class. */
class BikeShopJSONStore : BikeShopStore, AnkoLogger {

    val context: Context
    var bikes = mutableListOf<BikeModel>()

    /* This is the constructor for the class. It is setting the context and checking if the file
    exists. If it does, it deserializes the file. */
    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    /**
     * It returns a list of bikes.
     *
     * @return A list of BikeShopModel objects
     */
    override fun findAll(): MutableList<BikeModel> {
        return bikes
    }

    /**
     * It creates a new bike and adds it to the list of bikes.
     *
     * @param bike BikeShopModel - this is the object that is being created.
     */
    override fun create(bike: BikeModel) {
        bike.id = generateRandomId()
        bikes.add(bike)
        serialize()
    }


    /**
     * The function takes a BikeShopModel object as a parameter, finds the bike in the list of bikes,
     * updates the bike's properties, and then serializes the list of bikes
     *
     * @param bike BikeShopModel - this is the bike that is being updated.
     */
    override fun update(bike: BikeModel) {
        val bikesList = findAll() as ArrayList<BikeModel>
        var foundBike: BikeModel? = bikesList.find { p -> p.id == bike.id }
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


    /**
     * It serializes the bikes list into a JSON string and writes it to a file.
     */
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(bikes, listType)
        write(context, JSON_FILE, jsonString)
    }

    /**
     * It reads the JSON file and converts it into a list of Bike objects.
     */
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        bikes = Gson().fromJson(jsonString, listType)
    }

    /**
     * It removes the bike from the list of bikes.
     *
     * @param bike BikeShopModel - this is the bike that we want to delete from the list
     */
    override fun delete(bike: BikeModel) {
        bikes.remove(bike)
        serialize()
    }
}