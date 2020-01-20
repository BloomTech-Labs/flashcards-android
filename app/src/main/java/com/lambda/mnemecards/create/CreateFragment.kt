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

//            viewModel.

        })

        binding.btnCreateAddCard.setOnClickListener {
            viewModel.addCards()
            binding.rvCreateListCards.adapter?.notifyDataSetChanged()
        }


        // Inflate the layout for this fragment
        return binding.root
    }


}
