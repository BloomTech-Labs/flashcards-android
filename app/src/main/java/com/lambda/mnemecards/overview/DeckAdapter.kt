package com.lambda.mnemecards.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lambda.mnemecards.databinding.DeckItemBinding
import com.lambda.mnemecards.network.Deck

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class DeckAdapter(val onClickListener: OnClickListener):
    ListAdapter<Deck, DeckAdapter.DeckViewHolder>(DiffCallback){

    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Deck] information.
     */
    class DeckViewHolder(private var binding: DeckItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(deck: Deck) {
            binding.deck = deck
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Deck]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Deck>() {
        override fun areItemsTheSame(oldItem: Deck, newItem: Deck): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Deck, newItem: Deck): Boolean {
            return oldItem.name == newItem.name
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        return DeckViewHolder(DeckItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val currentDeck = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(currentDeck)
        }
        holder.bind(currentDeck)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Deck]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Deck]
     */
    class OnClickListener(val clickListener: (deck:Deck) -> Unit) {
        fun onClick(deck:Deck) = clickListener(deck)
    }


}