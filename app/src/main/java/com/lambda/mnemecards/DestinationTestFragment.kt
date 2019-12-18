package com.lambda.mnemecards


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_destination_test.*

class DestinationTestFragment : Fragment() {

    // Holds the data from the previous fragment
    private val args: DestinationTestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sets up the text views to use the data from the previous fragment.
        tv_first.text = args.valueOne
        tv_second.text = args.valueTwo.toString()

        btn_return.setOnClickListener {
            findNavController().navigate(R.id.action_destinationTestFragment_to_homeTestFragment)
        }
    }
}