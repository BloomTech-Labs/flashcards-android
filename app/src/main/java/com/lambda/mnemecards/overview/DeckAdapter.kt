package com.lambda.mnemecards.overview

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lambda.mnemecards.R
import com.lambda.mnemecards.databinding.DeckItemBinding
import com.lambda.mnemecards.network.Deck
import kotlinx.android.synthetic.main.deck_item.view.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class DeckAdapter(val onClickListener: OnClickListener):
    ListAdapter<Deck, DeckAdapter.DeckViewHolder>(DiffCallback){

    /**
     * The DeckViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Deck] information.
     */
    class DeckViewHolder(private var binding: DeckItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(deck: Deck) {
            binding.deck = deck
//            binding.tvTitle.text = deck.deckName
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
            return oldItem.deckName == newItem.deckName
        }

        override fun areContentsTheSame(oldItem: Deck, newItem: Deck): Boolean {
            return oldItem == newItem
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

        // Creates the pop up menu that allows for editing, achieving, and deleting.
        holder.itemView.tv_options.setOnClickListener {

            val popup = PopupMenu(holder.itemView.context, holder.itemView.tv_options)
            popup.inflate(R.menu.options_menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener() {
                when(it.itemId){
                    // If edit deck is clicked on, then pass in true to the onClick function I defined below
                    R.id.edit_deck -> {
                        onClickListener.onClick(currentDeck, true)
                        true
                    }
                    R.id.delete_deck -> {
                        val builder = AlertDialog.Builder(holder.itemView.context)
                        builder.setTitle("Delete Confirmation")
                        builder.setMessage("Sure you want to delete this deck?")
                        builder.setPositiveButton("Delete") { dialogInterface, i ->
                            holder.itemView.visibility = View.GONE
                            holder.itemView.layoutParams.height = 0
                            Toast.makeText(holder.itemView.context, "Deck has been successfully deleted", Toast.LENGTH_SHORT).show()
                        }
                        builder.setNegativeButton("<- No, go back"){dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                        builder.show()
                        true
                    }
                    else -> {
                        val builder = AlertDialog.Builder(holder.itemView.context)
                        builder.setTitle("Archive Confirmation")
                        builder.setMessage("Sure you want to archive this deck?")
                        builder.setPositiveButton("Archive") { dialogInterface, i ->
                        holder.itemView.visibility = View.GONE
                        holder.itemView.layoutParams.height = 0
                        }
                        builder.setNegativeButton("<- No, go back"){dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                        builder.show()
                        true
                    }
                }
            })
            popup.show()
        }
        holder.bind(currentDeck)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Deck]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Deck]
     */
    class OnClickListener(val clickListener: (deck:Deck, edit: Boolean) -> Unit) {
        // the onClick that only takes in a deck brings the user to studying
        fun onClick(deck:Deck) = clickListener(deck, false)

        // the onClick that takes in a deck and a boolean brings the user to editing the deck
        fun onClick(deck:Deck, edit: Boolean) = clickListener(deck, edit)
    }
}