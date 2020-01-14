package com.lambda.mnemecards.settings


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.SetOptions
import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentSettingsBinding
import com.lambda.mnemecards.network.User
import com.lambda.mnemecards.overview.db


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var viewModelFactory: SettingsViewModelFactory

    // For the prefer to study spinner
    private lateinit var preferToStudyByAdapter:ArrayAdapter<CharSequence>

    // For the study frequency spinner
    private lateinit var studyFrequencyAdapter:ArrayAdapter<CharSequence>

    // For the notification frequency spinner
    private lateinit var notificationFrequencyAdapter: ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        val settingsFragmentArgs by navArgs<SettingsFragmentArgs>()

        viewModelFactory = SettingsViewModelFactory(settingsFragmentArgs.name, settingsFragmentArgs.photoUrl, settingsFragmentArgs.userPreference)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SettingsViewModel::class.java)

        Log.i("SettingsFragment2", viewModel.user.value.toString())

//       binding.lifecycleOwner = this

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

//        binding.executePendingBindings()

        viewModel.name.observe(this, Observer{newName ->
            binding.tvSettingsName.text =newName.toString()
//            Toast.makeText(binding.root.context, "${binding.tvSettingsName.text} ${newName.toString()}", Toast.LENGTH_SHORT).show()
//            binding.executePendingBindings()
        })

        // For the prefer to study spinner
        preferToStudyByAdapter = ArrayAdapter.createFromResource(binding.root.context, R.array.study_methods, android.R.layout.simple_spinner_item )
        preferToStudyByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSettingsPreferToStudyBy.adapter = preferToStudyByAdapter
        binding.spinnerSettingsPreferToStudyBy.onItemSelectedListener = this

        // For the study frequency spinner
        studyFrequencyAdapter = ArrayAdapter.createFromResource(binding.root.context, R.array.study_frequency, android.R.layout.simple_spinner_item)
        studyFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStudyFrequency.adapter = studyFrequencyAdapter
        binding.spinnerStudyFrequency.onItemSelectedListener = this

        // For the notification frequency spinner
        notificationFrequencyAdapter = ArrayAdapter.createFromResource(binding.root.context, R.array.study_notification_frequency, android.R.layout.simple_spinner_item)
        notificationFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNotificationFrequency.adapter = notificationFrequencyAdapter
        binding.spinnerNotificationFrequency.onItemSelectedListener = this

        setDefaultSettings(viewModel.user.value!!, binding)

        binding.btnSettingsSave.setOnClickListener{
            savePreferences(viewModel.user.value!!, binding)
        }

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            R.id.preferences -> findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        Toast.makeText(parent?.context, parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()

        // parent?.getItemAtPosition(position).equals seems like an important function I'll be needing in the future
    }

    // Sets the default values of the radio buttons and spinners depending on what the user has previous saved as their preferences
    fun setDefaultSettings(user: User, binding: FragmentSettingsBinding){

        if(!user.favSubjects.isNullOrEmpty()){
            binding.etSettingsPreference.append(" " + user.favSubjects)
        }

        if(!user.customOrPremade.isNullOrEmpty()){
            if(user.mobileOrDesktop!!.toLowerCase() == "desktop"){
                binding.rbSettingsDesktop.isChecked = true
            }
            else{
                binding.rbSettingsMobile.isChecked = true
            }
        }

        if(!user.technique.isNullOrEmpty()){

            val spinnerPosition = preferToStudyByAdapter.getPosition(user.technique)
            binding.spinnerSettingsPreferToStudyBy.setSelection(spinnerPosition)
            Log.i("SettingsFragment", user.technique)
        }

        if(!user.studyFrequency.isNullOrEmpty()){
            val spinnerPosition = studyFrequencyAdapter.getPosition(user.studyFrequency)
            binding.spinnerStudyFrequency.setSelection(spinnerPosition)
            Log.i("settingsFragment2", user.studyFrequency)
        }

        if(!user.notificationFrequency.isNullOrEmpty()){
            val spinnerPosition = notificationFrequencyAdapter.getPosition(user.notificationFrequency)
            binding.spinnerNotificationFrequency.setSelection(spinnerPosition)
        }

        if(!user.customOrPremade.isNullOrEmpty()){
            if(user.customOrPremade!!.toLowerCase() == "custom"){
                binding.rbSettingsCustomDecks.isChecked = true
            }
            else{
                binding.rbSettingsPreMadeDecks.isChecked = true
            }
        }
    }

    fun savePreferences(user: User, binding: FragmentSettingsBinding){

//        if(binding.rgSettingsPreferencesMobileDesktop.isEnabled){
            val idMobileOrDesktop = binding.rgSettingsPreferencesMobileDesktop.checkedRadioButtonId
            val radioButtonMobileOrDesktop = binding.rgSettingsPreferencesMobileDesktop.findViewById<RadioButton>(idMobileOrDesktop)
            val radioIdMobileOrDesktop = binding.rgSettingsPreferencesMobileDesktop.indexOfChild(radioButtonMobileOrDesktop)
            val btnMobileOrDesktop = binding.rgSettingsPreferencesMobileDesktop.getChildAt(radioIdMobileOrDesktop)

            val idDecks = binding.rgSettingsPreferencesPreMadeCustom.checkedRadioButtonId
            val radioButtonDecks = binding.rgSettingsPreferencesPreMadeCustom.findViewById<RadioButton>(idDecks)
            val radioIdDecks = binding.rgSettingsPreferencesPreMadeCustom.indexOfChild(radioButtonDecks)
            val btnDecks = binding.rgSettingsPreferencesPreMadeCustom.getChildAt(radioIdDecks)
//        }

        val preferences = hashMapOf(
            "MobileOrDesktop" to btnMobileOrDesktop.tag.toString(),
            "customOrPremade" to btnDecks.tag.toString()
        )
        user.mobileOrDesktop = btnMobileOrDesktop.tag.toString()
        db.collection("Users").document(user.id.toString()).set(preferences, SetOptions.merge())
    }
}
