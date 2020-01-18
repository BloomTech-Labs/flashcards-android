package com.lambda.mnemecards.create


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.lambda.mnemecards.R
import com.lambda.mnemecards.cards.CardsViewModel
import com.lambda.mnemecards.databinding.FragmentCreateBinding

/**
 * A simple [Fragment] subclass.
 */
class CreateFragment : Fragment() {

    private lateinit var viewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreateBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create,
            container,
            false)

        viewModel = ViewModelProviders.of(this)
            .get(CreateViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }


}
