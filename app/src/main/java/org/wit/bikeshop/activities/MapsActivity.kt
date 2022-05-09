package org.wit.bikeshop.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.bikeshop.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.bikeshop.models.Location

/* This is the class that is used to create the map. */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    var location = Location()

    /**
     * > When a marker is clicked, set the marker's snippet to the current location
     *
     * @param marker The marker that was clicked.
     * @return The return value is a boolean.
     */
    override fun onMarkerClick(marker: Marker): Boolean {
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
        return false
    }

    /**
     * When the back button is pressed, the location is passed back to the calling activity
     */
    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }

    /**
     * Called when the user starts dragging a marker.
     *
     * @param marker The marker that was dragged.
     */
    override fun onMarkerDragStart(marker: Marker) {
    }

    /**
     * This function is called when a marker is dragged
     *
     * @param marker The marker that was dragged.
     */
    override fun onMarkerDrag(marker: Marker) {
    }

    /**
     * When the user stops dragging the marker, update the location object with the new latitude and
     * longitude
     *
     * @param marker The marker that was dragged.
     */
    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    /**
     * We're getting the location from the intent, and then we're getting the map fragment from the
     * layout, and then we're getting the map from the fragment
     *
     * @param savedInstanceState The saved instance state is a Bundle that contains the activity's
     * previously saved state. If the activity has never existed before, the value of the Bundle is
     * null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Bike")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)
        map.setOnMarkerClickListener(this)
        map.setOnMarkerDragListener(this)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))

    }


}