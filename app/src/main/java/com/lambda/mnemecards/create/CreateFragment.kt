package com.lambda.mnemecards.create


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.lambda.mnemecards.R
import com.lambda.mnemecards.cards.CardsViewModel
import com.lambda.mnemecards.databinding.FragmentCreateBinding
import com.lambda.mnemecards.overview.HomeViewModel

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        binding.rvCreateListCards.adapter = CardAdapter(CardAdapter.OnClickListener{
            binding.etCreateCardFront.setText(it.front)
            binding.etCreateCardBack.setText(it.back)
        })

        binding.btnCreateAddCard.setOnClickListener {
            viewModel.addCards(binding.etCreateCardFront.text.toString(), binding.etCreateCardBack.text.toString())
            binding.etCreateCardFront.text.clear()
            binding.etCreateCardBack.text.clear()
        }

        binding.btnCreateSaveDeck.setOnClickListener {
            viewModel.addDeck(binding.etCreateDeckName.text.toString())

            Log.i("CreateFragment", "${viewModel.decks.value?.get(0)?.data?.get(0)?.data?.front}")
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
