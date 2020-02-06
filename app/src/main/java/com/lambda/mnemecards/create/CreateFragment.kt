package com.lambda.mnemecards.create


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.lambda.mnemecards.R
import com.lambda.mnemecards.cards.CardsViewModel
import com.lambda.mnemecards.databinding.FragmentCreateBinding
import com.lambda.mnemecards.overview.HomeViewModel
import kotlinx.android.synthetic.main.fragment_create.*

/**
 * A simple [Fragment] subclass.
 */
class CreateFragment : Fragment() {

    private lateinit var viewModel: CreateViewModel
    private lateinit var viewModelFactory: CreateViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // boilerplate for data binding.
        val binding: FragmentCreateBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create,
            container,
            false)

        val createFragmentArgs by navArgs<CreateFragmentArgs>()

        val application = requireNotNull(activity).application

        viewModelFactory = CreateViewModelFactory(createFragmentArgs.deckList.toMutableList(), application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CreateViewModel::class.java)

        // Sets the binding's viewmodel from the xml to the viewModel created in this function.
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        // Set the edit texts equal to the cards values.
        binding.rvCreateListCards.adapter = CardAdapter(CardAdapter.OnClickListener{
//            binding.etCreateCardFront.setText(it.front)
//            binding.etCreateCardBack.setText(it.back)
        })

        // Sets the edit text values to blank for when a user clicks on add card.
        binding.btnCreateAddCard.setOnClickListener {
            viewModel.addCards(binding.etCreateCardFront.text.toString(), binding.etCreateCardBack.text.toString())
            binding.etCreateCardFront.text.clear()
            binding.etCreateCardBack.text.clear()
        }

        // Adds the deck to the database.
        binding.btnCreateSaveDeck.setOnClickListener {
//            viewModel.addDeck(binding.textInputCreateDeckName.inp.text.toString())

            Toast.makeText(binding.root.context, "Deck has been saved", Toast.LENGTH_SHORT).show()

            Log.i("CreateFragment", "${viewModel.decks.value?.get(0)?.data?.get(0)?.data?.front}")
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
