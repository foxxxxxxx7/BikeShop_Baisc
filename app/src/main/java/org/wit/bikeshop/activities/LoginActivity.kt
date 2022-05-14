package org.wit.bikeshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import org.wit.bikeshop.R
import org.wit.bikeshop.main.MainApp

/* This is a class declaration. It is a class called LoginActivity that inherits from
AppCompatActivity. */
class LoginActivity : AppCompatActivity() {

    /* This is a companion object. It is a singleton object that is associated with the class. */
    private companion object{
        private const val TAG = "LoginActivity"
        private const val GOOGLE_SIGN_IN = 1000
    }
    /* `lateinit var app: MainApp` is a lateinit variable. It is a non-nullable variable of type
    MainApp that is not initialized when it is declared. `private lateinit var auth: FirebaseAuth`
    is
    a lateinit variable. It is a non-nullable variable of type FirebaseAuth that is not initialized
    when it is declared. */
    lateinit var app: MainApp
    private lateinit var auth: FirebaseAuth



    /**
     * We're setting up the Google Sign In button to use the Google Sign In API, and we're telling it
     * to use the Google Sign In API to get the user's email and ID token
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(this, gso)
        sign_in_button.setOnClickListener{
            val signInIntent = client.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }
    }




    /**
     * > When the user signs in with Google, we get the user's ID token from Google, and then we use
     * that token to authenticate with Firebase
     *
     * @param requestCode The request code you passed to startActivityForResult().
     * @param resultCode The integer result code returned by the child activity through its
     * setResult().
     * @param data Intent? - The data returned by the activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            Log.d(TAG, "well der " + GoogleSignIn.getSignedInAccountFromIntent(data).toString())
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    /**
     * The onStart() function is called when the activity is started
     */
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    /**
     * If the user is not null, navigate to the BikeListActivity
     *
     * @param user The user object that contains the user's email address and password.
     * @return The user object is being returned.
     */
    private fun updateUI(user: FirebaseUser?) {
        if(user == null) {
            Log.w(TAG, "User is null, wont navigate further")
            return
        }
        Log.w(TAG, user.toString() + "1234567" )
        startActivity(Intent(this, BikeListActivity::class.java))
        finish()
    }

    /**
     * We get the idToken from the GoogleSignInAccount object, then we use that idToken to create a
     * GoogleAuthProvider credential, then we use that credential to sign in to Firebase
     *
     * @param idToken A Firebase credential.
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this,"Authentication Failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

}
