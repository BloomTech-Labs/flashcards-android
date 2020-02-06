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

        // Data Binding boilerplate code
        val binding: FragmentCardsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cards, container, false)

        val cardFragmentArgs by navArgs<CardsFragmentArgs>()

        val application = requireNotNull(activity).application

        viewModelFactory = CardsViewModelFactory(cardFragmentArgs.deck, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardsViewModel::class.java)

        // Sets the binding viewmodel from the XML to the viewModel that gets created in this function.
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.displayCard.observe(this, Observer {text->
            binding.tvCardsDisplay.text = text
//            binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#FFD164"))
        })

        // TODO: FIX THIS BOOLEAN LOGIC LATER
        // True = White
        // False = Orange
        // Changes the visibility of views depending on if the card is displaying the front or back of the card
        viewModel.frameLayoutColor.observe(this, Observer { color ->

            binding.btnCardsNext.visibility = View.INVISIBLE
//            binding.btnCardsTryAgain.visibility = View.INVISIBLE
            binding.ivCardsDisplayHowWell.visibility = View.INVISIBLE
            binding.tvCardsDisplay.visibility = View.VISIBLE

            if(!color){
                binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#FFD164"))

                binding.ivCardsFlip.visibility = View.INVISIBLE
                binding.tvCardsTapInstruction.visibility = View.INVISIBLE
                binding.tvCardsHowWell.visibility = View.VISIBLE
                binding.ivCardsShock.visibility = View.VISIBLE
                binding.ivCardsHappy.visibility = View.VISIBLE
                binding.ivCardsCool.visibility = View.VISIBLE
            }
            else{
                binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#F3ECE3"))

                binding.ivCardsFlip.visibility = View.VISIBLE
                binding.tvCardsTapInstruction.visibility = View.VISIBLE
                binding.tvCardsHowWell.visibility = View.INVISIBLE
                binding.ivCardsShock.visibility = View.INVISIBLE
                binding.ivCardsHappy.visibility = View.INVISIBLE
                binding.ivCardsCool.visibility = View.INVISIBLE
                binding.tvCardsEmojiToText.visibility = View.INVISIBLE
            }
        })

        // Changes the display if an emoji is clicked on.
        viewModel.clickedEmoji.observe(this, Observer { clickedEmoji ->

            binding.ivCardsDisplayHowWell.visibility = View.VISIBLE
            binding.flCardsDisplayCard.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.ivCardsShock.visibility = View.INVISIBLE
            binding.ivCardsHappy.visibility = View.INVISIBLE
            binding.ivCardsCool.visibility = View.INVISIBLE
            binding.tvCardsHowWell.visibility = View.INVISIBLE
            binding.tvCardsDisplay.visibility = View.INVISIBLE
            binding.tvCardsEmojiToText.visibility = View.VISIBLE

            binding.btnCardsNext.visibility = View.VISIBLE
//            binding.btnCardsTryAgain.visibility = View.VISIBLE

            // Display the Shocked Emoji if it gets clicked on
            if(clickedEmoji == "shockEmoji"){
                binding.ivCardsDisplayHowWell.setImageResource(R.drawable.shocked_emoji)
                binding.tvCardsEmojiToText.text = "Umm?!"
            }

            // Display the Happy Emoji if it gets clicked on
            else if(clickedEmoji == "happyEmoji"){
                binding.ivCardsDisplayHowWell.setImageResource(R.drawable.happy_emoji)
                binding.tvCardsEmojiToText.text = "Okay!"
            }

            // Display the Cool Emoji if it gets clicked on
            else{
                binding.ivCardsDisplayHowWell.setImageResource(R.drawable.cool_emoji)
                binding.tvCardsEmojiToText.text = "Nailed It!"
            }

        })

        // Inflate the layout for this fragment
        return binding.root
    }


}
