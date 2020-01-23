package com.lambda.mnemecards.edit


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
import com.lambda.mnemecards.create.CardAdapter
import com.lambda.mnemecards.databinding.FragmentEditBinding

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {

    private lateinit var viewModel: EditViewModel
    private lateinit var viewModelFactory: EditViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        val editFragmentArgs by navArgs<EditFragmentArgs>()

        val application = requireNotNull(activity).application

        viewModelFactory = EditViewModelFactory(editFragmentArgs.deck, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.rvEditCards.adapter = CardAdapter(CardAdapter.OnClickListener {
        })

        Log.i("EditFragment", "${viewModel.listOfCards.value?.get(0)?.front}")

        binding.btnEditDelete.setOnClickListener {
            var size = binding.rvEditCards.adapter!!.itemCount
            Log.i("Edit Fragment", size.toString())


//               val cardSelected = (binding.rvEditCards.adapter as CardAdapter).getItemId(cardCounter)
            val viewFirst = binding.rvEditCards.getChildAt(1)
            viewFirst.visibility = View.GONE
            viewFirst.layoutParams.height = 0

            val viewSecond = binding.rvEditCards.getChildAt(2)
            viewSecond.visibility = View.GONE
            viewSecond.layoutParams.height = 0

//            for(card in viewModel.listOfCards.value!!){
//                if(card.checked == true){
//                    it.visibility = View.GONE
//                    it.layoutParams.height = 0
//                }
//            }
        }

        binding.btnEditAddCardSecond.setOnClickListener {
            viewModel.addCard(binding.etCardFront.text.toString(), binding.etCardBack.text.toString())
            binding.etCardFront.setText("")
            binding.etCardBack.setText("")
        }

        return binding.root
    }


}
