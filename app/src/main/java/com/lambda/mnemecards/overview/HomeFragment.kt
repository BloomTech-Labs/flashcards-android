package com.lambda.mnemecards.overview


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    lateinit var fragmentContext: Context
    var googleSignInClient: GoogleSignInClient? = null
    val RC_SIGN_IN = 1000

    // For accessing Firestore
    private var db = FirebaseFirestore.getInstance()

    private var loggedInFlag: Boolean = false

    // Name to be passed to the settings
    var name: String? = "qwe"

    // Photo URL to be passed to the settings to draw the picture.
    var photoUrl: String? = "asd"

    private lateinit var auth: FirebaseAuth

    var callbackManager = CallbackManager.Factory.create()

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        fragmentContext = container!!.context


        binding.rvDecks.adapter = DeckAdapter(DeckAdapter.OnClickListener {

        })

        // Code that pops up the possible log in options

        if (!loggedInFlag) {
            launchSignInFlow()
            loggedInFlag = true
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val docRef = db.collection("DemoDeck").document("I2r2gejFYwCQfqafWlVy")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("Get Deck", "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d("Get Deck", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Get Deck", "get failed with ", exception)
//            }

        //This code is to get cards
//        db.collection("DemoDeck").document("I2r2gejFYwCQfqafWlVy").collection("Biology")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("Get Deck", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Get Deck", "Error getting documents: ", exception)
//            }
//
//            .addOnFailureListener { exception ->
//                Log.d("Get Deck", "Error getting documents: ", exception)
//            }

        //This code is to get decks
        db.collection("DemoDeck").document("I2r2gejFYwCQfqafWlVy")
            .get()
            .addOnSuccessListener { result ->
                Log.d(
                    "Get Deck",
                    "${result.id} => ${result.data} => ${result.reference} => ${result.metadata} =>"
                )

            }
            .addOnFailureListener { exception ->
                Log.d("Get Deck", "Error getting documents: ", exception)
            }

            .addOnFailureListener { exception ->
                Log.d("Get Deck", "Error getting documents: ", exception)
            }

//        db.collection("DemoDeck").document().collection("Biology").get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("Get Deck", "DocumentSnapshot data: ${document.documents}")
//                } else {
//                    Log.d("Get Deck", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Get Deck", "get failed with ", exception)
//            }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(fragmentContext, gso)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

//        btn_google_login.setOnClickListener {
//            signIn()
//        }
//
//        btn_google_signout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//        }
//
//        btn_facebook_login.setOnClickListener {
//            facebookLogin()
//        }
//
//        btn_register.setOnClickListener {
//            auth.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = auth.currentUser
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(fragmentContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }

//        btn_login.setOnClickListener {
//            auth.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
//                        val user = auth.currentUser
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(fragmentContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }

//        btn_destination.setOnClickListener{
//            if((!et_first.text.toString().isNullOrEmpty()) && (!et_second.text.toString().isNullOrEmpty())){
//                val directions = HomeFragmentDirections.actionHomeFragmentToDestinationTestFragment(et_first.text.toString(), et_second.text.toString().toInt())
//                findNavController().navigate(directions)
//            }
//        }
    }

    private fun signIn() {

        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Google Login Success")
                val user = auth.currentUser
                val message = " ${user?.displayName} + ${user?.email} + ${user?.photoUrl}"

                Log.i("INFORMATION GOOGLE", message)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
    }

    fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    firebaseAuthWithFacebook(result)
                }

                override fun onCancel() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    Log.i("INFORMATION FACEBOOK", "CANCEL")
                }

                override fun onError(error: FacebookException?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    Log.i("INFORMATION FACEBOOK", "ERROR")
                }

            })
    }

    fun firebaseAuthWithFacebook(result: LoginResult?) {
        var credential = FacebookAuthProvider.getCredential(result!!.accessToken.token)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Facebook Login Success")
                val user = auth.currentUser
                val message = " ${user?.displayName} + ${user?.email} + ${user?.photoUrl}"
                Log.i("INFORMATION FACEBOOK", message)
            }
        }
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
            SIGN_IN_RESULT_CODE
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

                    Toast.makeText(fragmentContext, "Welcome $name", Toast.LENGTH_SHORT).show()

                    Log.i("HomeFragment", name + email + photoUrl)
                }

                // User successfully signed in
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.preferences -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment(name, photoUrl))
        }

        return super.onOptionsItemSelected(item)
    }

}