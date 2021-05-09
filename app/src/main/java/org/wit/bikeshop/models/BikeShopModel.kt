package org.wit.bikeshop.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BikeShopModel(var id: Long = 0,
                         var title: String = "",
                         var size: String = "",
                         var style: String = "",
                         var gender: String = "",
                         var price: String = "",
                         var condition: String = "",
                         var comment: String = "",
                         var image: String = "",
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
