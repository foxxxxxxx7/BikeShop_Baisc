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


class BikeShopListActivity : AppCompatActivity(), BikeListener {

    lateinit var app: MainApp

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<BikeShopActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBikeClick(bike: BikeShopModel) {
        startActivityForResult(intentFor<BikeShopActivity>().putExtra("bike_edit", bike), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadBikes()
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loadBikes() {
        showBikes(app.bikes.findAll())
    }

    fun showBikes(bikes: List<BikeShopModel>) {
        recyclerView.adapter = BikeShopAdapter(bikes, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}
