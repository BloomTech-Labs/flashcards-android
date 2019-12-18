package com.lambda.mnemecards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Toast.makeText(this, "Firebase Connection Test", Toast.LENGTH_SHORT).show()




        //This makes it so that the when moving between fragments, there's a back button now.
        //Nav Host Fragment is the ID I gave to the fragment in the activity_main.xml
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
    }

    // Delegates the up button pressed to the nav controller to make the back button actually work now.
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
