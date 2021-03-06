package org.wit.bikeshop.models

import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger

/* > The BikeFirebase class is a Kotlin class that implements the BikeShopStore interface and the
AnkoLogger interface */
class BikeFirebase : BikeShopStore, AnkoLogger {
    var bikes = mutableListOf<BikeModel>()

    /* This is the URL of the Firebase database. */
    private val db =
        FirebaseDatabase.getInstance("https://bikeshop-basic-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference().child("bikes")
    // private val db = FirebaseDatabase.getInstance().reference.child("bikes")

    /**
     * It returns a list of bikes.
     *
     * @return A list of BikeModel objects
     */
    override fun findAll(): MutableList<BikeModel> {
        return bikes
    }

   /**
     * It creates the bike in the database.
     *
     * @param bike BikeModel - The bike object that we want to update.
     */
     override fun create(bike: BikeModel) {
        db.child(bike.id.toString()).setValue(bike)
    }

    /**
     * It updates the bike in the database.
     *
     * @param bike BikeModel - The bike object that we want to update.
     */
    override fun update(bike: BikeModel) {
        db.child(bike.id.toString()).setValue(bike)
    }

    /**
     * It deletes the bike from the database.
     *
     * @param bike BikeModel - The bike to be deleted
     */
    override fun delete(bike: BikeModel) {
        db.child(bike.id.toString()).removeValue()
    }
}