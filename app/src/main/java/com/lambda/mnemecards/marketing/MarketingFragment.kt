package com.lambda.mnemecards.marketing


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.lambda.mnemecards.MainActivity

import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentMarketingBinding
import com.lambda.mnemecards.network.User
import com.lambda.mnemecards.settings.SettingsViewModel

/**
 * A simple [Fragment] subclass.
 */

// For accessing Firestore
var db = FirebaseFirestore.getInstance()

class marketingFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    lateinit var fragmentContext: Context
    var googleSignInClient: GoogleSignInClient? = null
    val RC_SIGN_IN = 1000

    // Name to be passed to the settings
    var name: String? = "First Name"

    // Photo URL to be passed to the settings to draw the picture.
    var photoUrl: String? = "asd"

    // User's Id
    var userId: String?= "No Token"

    var user: User = User()

    lateinit var viewModel: MarketingViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentMarketingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_marketing,
            container,
            false
        )

        FirebaseAnalytics.getInstance(binding.root.context).setCurrentScreen(MainActivity(), "MarketingFragment", "Test2")

        // To get data using custom objects
        val docRef = db.collection("Users").document("4uR0bkDdUeOvQdolIXbiP0kxLZs1")
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject(User::class.java)!!
            Log.i("HomeFragment2", user?.favSubjects)
            Log.i("HomeFragment2", user?.mobileOrDesktop)
        }

        viewModel = ViewModelProviders.of(this)
            .get(MarketingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnMarketingSignIn.setOnClickListener {

            // Used for analytics in the firebase console
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Sign_in")
            FirebaseAnalytics.getInstance(binding.root.context).logEvent("Sign_In_Clicked", bundle)
            launchSignInFlow()
        }
        binding.btnMarketingSignUp.setOnClickListener {
            
            // Used for analytics in the firebase console
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Sign_up")
            FirebaseAnalytics.getInstance(binding.root.context).logEvent("Sign_Up_Clicked", bundle)
            launchSignInFlow()
        }

        fragmentContext = binding.root.context

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email, Google account, or Facebook account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()

            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
//                .setLogo(R.drawable.fui_ic_facebook_white_22dp)
                .build(),
            marketingFragment.SIGN_IN_RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {

                // For getting the user information
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    name = user?.displayName.toString()
                    val email = user.email
                    photoUrl = user?.photoUrl.toString()
                    userId = user?.uid

                    Toast.makeText(fragmentContext, "Welcome $name", Toast.LENGTH_SHORT).show()

                    Log.i("HomeFragment", name + email + photoUrl)

                    // To get user's preference data using custom objects
                    val docRef = db.collection("Users").document(userId!!)
                    docRef.get().addOnSuccessListener { documentSnapshot ->
                        Log.i("MarketingFragmentFix", documentSnapshot.exists().toString())
                        // Check to see if the user has any preferences or not.
                        if(documentSnapshot.exists())
                            this.user = documentSnapshot.toObject(User::class.java)!!
                        // If they don't have any preferences, assign default values to them.
                        else{
                            // Creates a hashMapOf to be writen to FireStore
                            val preferences = hashMapOf(
                                "MobileOrDesktop" to "Mobile",
                                "customOrPremade" to "pre-made",
                                "favSubjects" to "",
                                "notification-frequency" to "Everyday",
                                "study-frequency" to "Everyday",
                                "technique" to "Other",
                                "id" to user.uid
                            )
                            Log.i("MarketingFragment2", user.uid)
                            this.user.customOrPremade = "pre-made"
                            this.user.technique = "Other"
                            this.user.id = user.uid
                            this.user.mobileOrDesktop = "Mobile"
                            this.user.notificationFrequency = "Everyday"
                            this.user.studyFrequency = "Everyday"
                            this.user.favSubjects = ""
                            db.collection("Users").document(user.uid.toString()).set(preferences, SetOptions.merge())
                        }
                    }

                    // User successfully signed in
                    Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
                    this.findNavController().navigate(marketingFragmentDirections.actionMarketingFragmentToHomeFragment(name, photoUrl, this.user))
                }

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }
}

