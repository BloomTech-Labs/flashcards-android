package com.lambda.mnemecards


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class LoginFragment : Fragment() {

    lateinit var fragmentContext: Context
    var googleSignInClient: GoogleSignInClient ?= null
    val RC_SIGN_IN = 1000

    // For accessing Firestore
    private var db = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    var callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentContext = container!!.context
        return inflater.inflate(R.layout.fragment_login, container, false)
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
        db.collection("DemoDeck").document("I2r2gejFYwCQfqafWlVy").collection("Biology")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Get Deck", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Get Deck", "Error getting documents: ", exception)
            }

            .addOnFailureListener { exception ->
                Log.d("Get Deck", "Error getting documents: ", exception)
            }

        //This code is to get decks
        db.collection("DemoDeck").document("I2r2gejFYwCQfqafWlVy")
            .get()
            .addOnSuccessListener { result ->
                    Log.d("Get Deck", "${result.id} => ${result.data}")

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

        btn_google_login.setOnClickListener {
            signIn()
        }

        btn_google_signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }

        btn_facebook_login.setOnClickListener {
            facebookLogin()
        }

        btn_register.setOnClickListener {
            auth.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(fragmentContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btn_login.setOnClickListener {
            auth.signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(fragmentContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btn_destination.setOnClickListener{
            if((!et_first.text.toString().isNullOrEmpty()) && (!et_second.text.toString().isNullOrEmpty())){
                val directions = LoginFragmentDirections.actionLoginFragmentToDestinationTestFragment(et_first.text.toString(), et_second.text.toString().toInt())
                findNavController().navigate(directions)
            }
        }
    }

    private fun signIn() {

        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){

            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{task ->
            if(task.isSuccessful){
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

    fun facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
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

    fun firebaseAuthWithFacebook(result: LoginResult?){
        var credential = FacebookAuthProvider.getCredential(result!!.accessToken.token)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{task ->
            if(task.isSuccessful){
                println("Facebook Login Success")
                val user = auth.currentUser
                val message = " ${user?.displayName} + ${user?.email} + ${user?.photoUrl}"
                Log.i("INFORMATION FACEBOOK", message)
            }
        }
    }


}