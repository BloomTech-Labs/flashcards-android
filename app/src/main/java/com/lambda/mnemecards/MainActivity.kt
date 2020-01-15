package com.lambda.mnemecards

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.analytics.FirebaseAnalytics
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var fireAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val drawable: Drawable? = ContextCompat.getDrawable(applicationContext, R.drawable.fui_ic_facebook_white_22dp)

        fireAnalytics = FirebaseAnalytics.getInstance(this)

        printHashKey(this)

//        Toast.makeText(this, "Firebase Connection Test", Toast.LENGTH_SHORT).show()

        // Gets rid of the shadow under the action bar
        supportActionBar?.elevation = 0F


        //This makes it so that the when moving between fragments, there's a back button now.
        //Nav Host Fragment is the ID I gave to the fragment in the activity_main.xml
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
    }

    // Delegates the up button pressed to the nav controller to make the back button actually work now.
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("printHashKey", "printHashKey() Hash Key: $hashKey")

            }
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: Exception) {
        }
    }
}

