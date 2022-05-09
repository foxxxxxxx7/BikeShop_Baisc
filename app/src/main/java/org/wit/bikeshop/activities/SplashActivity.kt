package org.wit.bikeshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.wit.bikeshop.R

/* This class is the first activity that the user sees when they open the app. It displays a logo and
then after 3 seconds, it opens the Login activity */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, BikeShopListActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}