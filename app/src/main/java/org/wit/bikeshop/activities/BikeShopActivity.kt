package org.wit.bikeshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_bikeshop.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.bikeshop.R
import org.wit.bikeshop.helpers.readImage
import org.wit.bikeshop.helpers.readImageFromPath
import org.wit.bikeshop.helpers.showImagePicker
import org.wit.bikeshop.main.MainApp
import org.wit.bikeshop.models.BikeShopModel
import org.wit.bikeshop.models.Location

class BikeShopActivity : AppCompatActivity(), AnkoLogger {

    val LOCATION_REQUEST = 2
    val IMAGE_REQUEST = 1
    var edit = false
    var bike = BikeShopModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikeshop)
        app = application as MainApp


        if (intent.hasExtra("bike_edit")) {
            edit = true
            bike = intent.extras?.getParcelable<BikeShopModel>("bike_edit")!!
            bikeTitle.setText(bike.title)
            bikeSize.setText(bike.size)
            bikeStyle.setText(bike.style)
            bikeGender.setText(bike.gender)
            bikePrice.setText(bike.price)
            bikeCondition.setText(bike.condition)
            bikeComment.setText(bike.comment)
            bikeImage.setImageBitmap(readImageFromPath(this, bike.image))
            if (bike.image != null) {
                chooseImage.setText(R.string.change_bike_image)
            }
            btnAdd.setText(R.string.save_bike)
        }

        bikeLocation.setOnClickListener {
            val location = Location(52.260727672924894, -7.106110453605653, 15f)
            if (bike.zoom != 0f) {
                location.lat = bike.lat
                location.lng = bike.lng
                location.zoom = bike.zoom
            }
            startActivityForResult(
                intentFor<MapsActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
        }

        bikeLocation2.setOnClickListener {
            val location2 = Location(52.093074, -7.622118, 15f)
            if (bike.zoom != 0f) {
                location2.lat = bike.lat
                location2.lng = bike.lng
                location2.zoom = bike.zoom
            }
            startActivityForResult(
                intentFor<MapsActivity>().putExtra("location", location2),
                LOCATION_REQUEST
            )
        }





        btnAdd.setOnClickListener() {
            bike.title = bikeTitle.text.toString()
            bike.size = bikeSize.text.toString()
            bike.style = bikeStyle.text.toString()
            bike.gender = bikeGender.text.toString()
            bike.price = bikePrice.text.toString()
            bike.condition = bikeCondition.text.toString()
            bike.comment = bikeComment.text.toString()
            if (bike.title.isEmpty()) {
                toast(R.string.enter_bike_title)
            } else {
                if (edit) {
                    app.bikes.update(bike.copy())
                } else {
                    app.bikes.create(bike.copy())
                }
            }
            info("Add Button Pressed: $bikeTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    bike.image = data.getData().toString()
                    bikeImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_bike_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    bike.lat = location.lat
                    bike.lng = location.lng
                    bike.zoom = location.zoom
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bike, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.bikes.delete(bike)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}