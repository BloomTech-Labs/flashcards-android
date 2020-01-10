package com.lambda.mnemecards.cards


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentCardsBinding

/**
 * A simple [Fragment] subclass.
 */
class CardsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentCardsBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val selectedDeck = CardsFragmentArgs.fromBundle(arguments!!).deck
        val viewModelFactory = CardsViewModelFactory(selectedDeck, application)
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardsViewModel::class.java)

        // Inflate the layout for this fragment
        return binding.root
    }


}
