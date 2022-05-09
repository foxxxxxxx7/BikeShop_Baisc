package org.wit.bikeshop.activities

import BikeListener
import BikeShopAdapter
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bikeshop_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

import org.wit.bikeshop.R
import org.wit.bikeshop.main.MainApp
import org.wit.bikeshop.models.BikeShopModel


/* This is the class declaration. It is a class called BikeShopListActivity that inherits from
AppCompatActivity and implements BikeListener. */
class BikeShopListActivity : AppCompatActivity(), BikeListener {

    lateinit var app: MainApp

    /**
     * The onCreate function is called when the activity is created. It sets the content view to the
     * activity_bikeshop_list layout, and then calls the loadBikes function to populate the
     * recyclerView with the bike data
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state. If
     * the activity has never existed before, the value of the Bundle object is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikeshop_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadBikes()

        //enable action bar and set title
       // toolbar.title = title
//        setSupportActionBar(toolbar)
    }

    /**
     * This function inflates the menu resource file into the menu object
     *
     * @param menu The menu to inflate.
     * @return The superclass's onCreateOptionsMenu method is being returned.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * When the user selects the add menu item, start the BikeShopActivity and wait for a result.
     *
     * @param item The menu item that was selected.
     * @return The superclass method is being returned.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<BikeShopActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * > When a bike is clicked, start the BikeShopActivity and pass the bike object to it
     *
     * @param bike BikeShopModel - this is the bike that was clicked on
    /**
     * > When the user returns from the AddBikeActivity, load the bikes from the database and then call
     * the superclass's onActivityResult function
     *
     * @param requestCode This is the request code that you passed to startActivityForResult().
     * @param resultCode The integer result code returned by the child activity through its
     * setResult().
     * @param data The data returned from the activity.
     */
     */
    override fun onBikeClick(bike: BikeShopModel) {
    /**
     * > The function `loadBikes()` is private, and it calls the `showBikes()` function, passing in the
     * result of the `findAll()` function, which is called on the `bikes` property of the `app` object
     */
        startActivityForResult(intentFor<BikeShopActivity>().putExtra("bike_edit", bike), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadBikes()
        super.onActivityResult(requestCode, resultCode, data)
    }


    /**
     * > This function takes a list of BikeShopModel objects and sets the adapter of the recyclerView
     * to a BikeShopAdapter object, passing in the list of BikeShopModel objects and the context of the
     * activity
     *
     * @param bikes List<BikeShopModel> - this is the list of bikes that will be displayed in the
     * recycler view.
     */
    private fun loadBikes() {
        showBikes(app.bikes.findAll())
    }

    /**
     * > This function takes a list of BikeShopModel objects and sets the adapter of the recyclerView
     * to a BikeShopAdapter object, passing in the list of BikeShopModel objects and the context of the
     * activity
     *
     * @param bikes List<BikeShopModel> - this is the list of bikes that will be displayed in the
     * recycler view.
     */
    fun showBikes(bikes: List<BikeShopModel>) {
        recyclerView.adapter = BikeShopAdapter(bikes, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}
