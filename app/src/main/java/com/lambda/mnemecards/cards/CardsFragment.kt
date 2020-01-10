package com.lambda.mnemecards.cards


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.FragmentCardsBinding

/**
 * A simple [Fragment] subclass.
 */
class CardsFragment : Fragment() {

    private lateinit var viewModel: CardsViewModel
    private lateinit var viewModelFactory: CardsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCardsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cards, container, false)

        val cardFragmentArgs by navArgs<CardsFragmentArgs>()

        val application = requireNotNull(activity).application

        viewModelFactory = CardsViewModelFactory(cardFragmentArgs.deck, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.displayCard.observe(this, Observer {text->
            binding.tvCardsDisplay.text = text
//            binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#FFD164"))
        })

        // TODO: FIX THIS BOOLEAN LOGIC LATER
        // True = White
        // False = Orange
        viewModel.frameLayoutColor.observe(this, Observer { color ->
            if(!color){
                binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#FFD164"))
            }
            else{
                binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#F3ECE3"))
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}