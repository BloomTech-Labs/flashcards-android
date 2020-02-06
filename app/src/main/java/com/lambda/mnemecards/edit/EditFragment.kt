package com.lambda.mnemecards.edit


import android.app.AlertDialog
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

        // binding boiler plate
        val binding: FragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        val editFragmentArgs by navArgs<EditFragmentArgs>()

        val application = requireNotNull(activity).application

        viewModelFactory = EditViewModelFactory(editFragmentArgs.deck, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditViewModel::class.java)

        // sets the binding viewmodel from the xml to the binding created in this function.
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        // Creates a toast for when the user clicks on the save deck button.
        binding.btnEditSaveDeck.setOnClickListener {
            Toast.makeText(binding.root.context, "Deck has been saved", Toast.LENGTH_SHORT).show()
        }

        binding.rvEditCards.adapter = CardAdapter(CardAdapter.OnClickListener {
        })

        Log.i("EditFragment", "${viewModel.listOfCards.value?.get(0)?.front}")

        // Creates the delete alert dialog for when a user wants to delete a deck
        binding.btnEditDelete.setOnClickListener {
            var size = binding.rvEditCards.adapter!!.itemCount
            Log.i("Edit Fragment", size.toString())

                val builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle("Delete Confirmation")
                builder.setMessage("Sure you want to delete these cards?")
                builder.setPositiveButton("Delete") { dialogInterface, i ->
                    val viewFirst = binding.rvEditCards.getChildAt(1)
                    if(viewFirst != null){
                        // Sets the deck's views to nothing
                        viewFirst.visibility = View.GONE
                        viewFirst.layoutParams.height = 0
                    }
                    Toast.makeText(binding.root.context, "Card has been successfully deleted", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("<- No, go back"){dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                builder.show()
//               val cardSelected = (binding.rvEditCards.adapter as CardAdapter).getItemId(cardCounter)


//            val viewSecond = binding.rvEditCards.getChildAt(2)
//            viewSecond.visibility = View.GONE
//            viewSecond.layoutParams.height = 0

//            for(card in viewModel.listOfCards.value!!){
//                if(card.checked == true){
//                    it.visibility = View.GONE
//                    it.layoutParams.height = 0
//                }
//            }
        }

        // Shows the card that can be edited while also making the add btn invisible
        binding.btnEditAddCard.setOnClickListener {
            binding.btnEditAddCard.visibility = View.GONE
            binding.cardEditAdd.visibility = View.VISIBLE
            binding.btnEditAddCardSecond.visibility = View.VISIBLE
        }

        // Adds the card into the list while also hiding the card when it gets added.
        binding.btnEditAddCardSecond.setOnClickListener {
            viewModel.addCard(binding.etCardFront.text.toString(), binding.etCardBack.text.toString())
            binding.etCardFront.setText("")
            binding.etCardBack.setText("")

            binding.btnEditAddCard.visibility = View.VISIBLE
            binding.cardEditAdd.visibility = View.GONE
            binding.btnEditAddCardSecond.visibility = View.GONE
            Toast.makeText(application, "Card Added", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


}
