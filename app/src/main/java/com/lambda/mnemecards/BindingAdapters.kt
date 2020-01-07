package com.lambda.mnemecards

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lambda.mnemecards.network.Deck
import com.lambda.mnemecards.overview.DeckAdapter

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Deck>?) {
    val adapter = recyclerView.adapter as DeckAdapter
    adapter.submitList(data)
}