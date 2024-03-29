package org.wit.bikeshop.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bike.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.bikeshop.R
import org.wit.bikeshop.helpers.readImage
import org.wit.bikeshop.helpers.readImageFromPath
import org.wit.bikeshop.helpers.showImagePicker
import org.wit.bikeshop.main.MainApp
import org.wit.bikeshop.models.BikeModel
import org.wit.bikeshop.models.Location
import java.util.*


/* This is the start of the BikeActivity class. It is a subclass of AppCompatActivity and
implements AnkoLogger. It also declares some variables. */
class BikeActivity : AppCompatActivity(), AnkoLogger {

    //val LOCATION_REQUEST2 = 2
    val LOCATION_REQUEST = 2
    val IMAGE_REQUEST = 1
    var edit = false
    var bike = BikeModel()
    lateinit var app: MainApp

    fun generateRandomId(): Long {
        return Random().nextLong()
    }


    /* This is the start of the BikeActivity class. It is a subclass of AppCompatActivity and
    implements AnkoLogger. It also declares some variables. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike)
        app = application as MainApp


        /* This is checking if the intent has an extra called bike_edit. If it does, it sets the edit
        variable to true, sets the bike variable to the bike_edit extra, and sets the text of the
        bikeTitle, bikeSize, bikeStyle, bikeGender, bikePrice, bikeCondition, bikeComment, and
        bikeImage to the bike's title, size, style, gender, price, condition, comment, and image. It
        also sets the text of the chooseImage to change_bike_image if the bike's image is not null.
        It also sets the text of the btnAdd to save_bike. */
        if (intent.hasExtra("bike_edit")) {
            edit = true
            bike = intent.extras?.getParcelable<BikeModel>("bike_edit")!!
            bikeTitle.setText(bike.title)
            bikeSize.setSpinnerText(bike.size)
            bikeStyle.setSpinnerText(bike.style)
            bikeGender.setSpinnerText(bike.gender)
            bikePrice.setText(bike.price.toString())
            bikeCondition.setSpinnerText(bike.condition)
            bikeComment.setText(bike.comment)
            bikeImage.setImageBitmap(readImageFromPath(this, bike.image))
            if (bike.image != null) {
                chooseImage.setText(R.string.change_bike_image)
            }
            btnAdd.setText(R.string.save_bike)
        }

        /* This is the code that is used to set the location of the bike shop. */
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

//        bikeLocation2.setOnClickListener {
//            val location = Location(52.093074, -7.622118, 15f)
//            if (bike.zoom != 0f) {
//                location.lat = bike.lat
//                location.lng = bike.lng
//                location.zoom = bike.zoom
//            }
//            startActivityForResult(
//                intentFor<MapsActivity>().putExtra("location", location),
//                LOCATION_REQUEST
//            )
//        }

        /* This is the code that is used to add a bike to the database. */
        btnAdd.setOnClickListener() {
            bike.title = bikeTitle.text.toString()
            bike.size = bikeSize.selectedItem.toString()
            bike.style = bikeStyle.selectedItem.toString()
            bike.gender = bikeGender.selectedItem.toString()
            bike.price = bikePrice.text.toString().toDouble()
            bike.condition = bikeCondition.selectedItem.toString()
            bike.comment = bikeComment.text.toString()
            if (bike.title.isEmpty()) {
                toast(R.string.enter_bike_title)
            }
            if (bike.size.isEmpty()) {
                toast(R.string.enter_bike_size)
            }
            if (bike.style.isEmpty()) {
                toast(R.string.enter_bike_style)
            }
            if (bike.gender.isEmpty()) {
                toast(R.string.enter_bike_gender)
            }
            if (bike.price.toString().isEmpty()) {
                toast(R.string.enter_bike_price)
            }
            if (bike.condition.isEmpty()) {
                toast(R.string.enter_bike_condition)
            }
            if (bike.comment.isEmpty()) {
                toast(R.string.enter_bike_comment)
            }
            else {
                if (edit) {
                    app.bikes.update(bike.copy())
                } else {
                    bike.id = generateRandomId()
                    app.bikes.create(bike.copy())
                }
            }
            info("Add Button Pressed: $bikeTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
        //toolbarAdd.title = title
//        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    /* A function that is used to set the text of a spinner. Got this from stackoverflow */
    fun Spinner.setSpinnerText(text: String) {
        for (i in 0 until this.adapter.count) {
            if (this.adapter.getItem(i).toString().contains(text)) {
                this.setSelection(i)
            }
        }
    }


    /**
     * The function is called when the user returns from the activity that was started with
     * startActivityForResult()
     *
     * @param requestCode The request code you passed to startActivityForResult().
     * @param resultCode The integer result code returned by the child activity through its
     * setResult().
     * @param data Intent? - The data returned by the activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    bike.image = data.getData().toString()
                    println("IN IMAGE")
                    println(bike.image)
                    println(data.data.toString())
                    println(data.data?.encodedPath)
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
            /*LOCATION_REQUEST2 -> {
                if (data != null) {
                    val location2 = data.extras?.getParcelable<Location>("location2")!!

                    bike.lat = location2.lat
                    bike.lng = location2.lng
                    bike.zoom = location2.zoom

                }
            }*/
        }
    }

    /**
     * It inflates the menu and sets the visibility of the menu item to true.
     *
     * @param menu The menu to inflate.
     * @return The superclass's onCreateOptionsMenu method is being returned.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bike, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * When the user selects an item from the menu, if the item is the delete item, delete the bike and
     * finish the activity. If the item is the cancel item, finish the activity
     *
     * @param item MenuItem - the menu item that was selected
     * @return The superclass method is being returned.
     */
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