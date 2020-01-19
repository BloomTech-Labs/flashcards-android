package com.lambda.mnemecards

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambda.mnemecards.create.CardAdapter
import com.lambda.mnemecards.network.DataX
import com.lambda.mnemecards.network.Deck
import com.lambda.mnemecards.overview.DeckAdapter

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listDataDeck")
fun bindRecyclerViewDeck(recyclerView: RecyclerView, data: List<Deck>?) {
    val adapter = recyclerView.adapter as DeckAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataCard")
fun bindRecyclerViewCard(recyclerView: RecyclerView, data: List<DataX>?){
    val adapter = recyclerView.adapter as CardAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
//            .apply(
//                RequestOptions()
//                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}