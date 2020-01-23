package com.lambda.mnemecards.overview


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.facebook.CallbackManager
import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.card_item.view.*
import kotlinx.android.synthetic.main.deck_item.view.*
import kotlinx.android.synthetic.main.fragment_create.view.*


class HomeFragment : Fragment() {

    private var loggedInFlag: Boolean = false

    var callbackManager = CallbackManager.Factory.create()

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var fragmentContext: Context

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val homeFragmentArgs by navArgs<HomeFragmentArgs>()

        viewModelFactory = HomeViewModelFactory(
            homeFragmentArgs.name,
            homeFragmentArgs.photoUrl,
            homeFragmentArgs.user
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        val binding = FragmentHomeBinding.inflate(inflater)

        binding.pbLoading.visibility = View.VISIBLE

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        fragmentContext = container!!.context

        // Sets the adapter of the deckGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our Deck is clicked
        // If edit is false, then bring the user to study. If it's true, then bring the user to editing the deck.
        binding.rvDecks.adapter = DeckAdapter(DeckAdapter.OnClickListener { selectedDeck, edit ->
            if(edit == false)
                viewModel.displayDeckDetails(selectedDeck)
            else{
                viewModel.editDeckDetails(selectedDeck)
            }
        })

        viewModel.decks.observe(this, Observer {
            binding.pbLoading.visibility = View.INVISIBLE
        })

        // Navigates to the edit deck
        viewModel.navifateToEditSelectedDeck.observe(this, Observer {
            if(it != null){
                this.findNavController()
                    .navigate(com.lambda.mnemecards.overview.HomeFragmentDirections.actionHomeFragmentToEditFragment(it))
                viewModel.displayDeckEditComplete()
            }
        })

        // Navigates to study
        viewModel.navigateToSelectedDeck.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToCardsFragment(it))
                viewModel.displayDeckDetailsComplete()
            }
        })

        binding.btnCreateDeck.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreateFragment(
                viewModel.decks.value!!.toTypedArray()))
        }

        // Code that pops up the possible log in options

        if (!loggedInFlag) {
//            launchSignInFlow()
            loggedInFlag = true
        }

        // Testing out write with firestore

//        val preferences = hashMapOf(
//            "id" to "4uR0bkDdUeOvQdolIXbiP0kxLZs1",
//            "favSubjects" to "math",
//            "MobileOrDesktop" to "Desktop",
//            "customOrPremade" to "pre-made",
//            "notification-frequency" to "When I haven't met my goal in a week",
//            "studyFrequency" to "Twice a week",
//            "technique" to "Writing"
//        )

//        // To write Data
//        db.collection("Users").document("4uR0bkDdUeOvQdolIXbiP0kxLZs1")
//            .set(preferences, SetOptions.merge())
//            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


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
//        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(fragmentContext, gso)

        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()

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

//    private fun signIn() {
//
//        val signInIntent = googleSignInClient?.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//
//    }


//    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
//        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                println("Google Login Success")
//                val user = auth.currentUser
//                val message = " ${user?.displayName} + ${user?.email} + ${user?.photoUrl}"
//
//                Log.i("INFORMATION GOOGLE", message)
//            }
//        }
//    }

    override fun onStart() {
        super.onStart()

//        val currentUser = auth.currentUser
    }

//    fun facebookLogin() {
//        LoginManager.getInstance()
//            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
//        LoginManager.getInstance()
//            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult?) {
//                    firebaseAuthWithFacebook(result)
//                }
//
//                override fun onCancel() {
////                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    Log.i("INFORMATION FACEBOOK", "CANCEL")
//                }
//
//                override fun onError(error: FacebookException?) {
////                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    Log.i("INFORMATION FACEBOOK", "ERROR")
//                }
//
//            })
//    }

//    fun firebaseAuthWithFacebook(result: LoginResult?) {
//        var credential = FacebookAuthProvider.getCredential(result!!.accessToken.token)
//        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                println("Facebook Login Success")
//                val user = auth.currentUser
//                val message = " ${user?.displayName} + ${user?.email} + ${user?.photoUrl}"
//                Log.i("INFORMATION FACEBOOK", message)
//            }
//        }
//    }


    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.preferences -> findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSettingsFragment(
                    viewModel.name,
                    viewModel.photo,
                    viewModel.user
                )
            )
            R.id.logout -> {

                findNavController().navigate(R.id.action_homeFragment_to_marketingFragment)
            }
        }


        return super.onOptionsItemSelected(item)
    }

}