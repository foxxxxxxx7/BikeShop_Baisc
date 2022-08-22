package org.wit.bikeshop.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data class that holds the data for a bike and it's corresponding shop.
 * @property {Long} id - The id of the bike.
 * @property {String} title - The title of the bike.
 * @property {String} size - The size of the bike.
 * @property {String} style - The style of the bike.
 * @property {String} gender - The gender of the bike.
 * @property {String} price - The price of the bike
 * @property {String} condition - The condition of the bike.
 * @property {String} comment - The comment the user entered when they created the bike.
 * @property {String} image - The image of the bike.
 * @property {Double} lat - The latitude of the bike shop
 * @property {Double} lng - The longitude of the bike shop
 * @property {Float} zoom - The zoom level of the map.
 */
@Parcelize
data class BikeModel(
    var id: Long = 0,
    var title: String = "",
    var size: String = "",
    var style: String = "",
    var gender: String = "",
    var price: Double = 0.0,
    var condition: String = "",
    var comment: String = "",
    var image: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable

/**
 * Location is a data class that has three properties: lat, lng, and zoom.
 *
 * The lat and lng properties are of type Double and have default values of 0.0. The zoom property is
 * of type Float and has a default value of 0f.
 *
 * The @Parcelize annotation is a Kotlin annotation that tells the Kotlin compiler to generate the
 * necessary code to make this class Parcelable.
 *
 * The Parcelable interface is an Android interface that allows you to pass objects between Activities.
 *
 * The Parcelable interface is similar to the Serializable
 * @property {Double} lat - The latitude of the location.
 * @property {Double} lng - The longitude of the location.
 * @property {Float} zoom - The zoom level of the map.
 */
@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable
