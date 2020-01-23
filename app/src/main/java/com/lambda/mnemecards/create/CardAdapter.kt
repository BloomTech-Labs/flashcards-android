package com.lambda.mnemecards.create

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lambda.mnemecards.databinding.CardItemBinding
import com.lambda.mnemecards.network.Card
import com.lambda.mnemecards.network.DataX
import kotlinx.android.synthetic.main.card_item.view.*
import kotlinx.android.synthetic.main.deck_item.view.*
import kotlinx.android.synthetic.main.fragment_create.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class CardAdapter(val onClickListener: OnClickListener):
    ListAdapter<DataX, CardAdapter.CardViewHolder>(DiffCallback){

    /**
     * The CardViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Deck] information.
     */
    class CardViewHolder(private var binding: CardItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: DataX) {
            binding.card = card
//            binding.tvTitle.text = deck.deckName
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [DataX]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<DataX>() {
        override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.front == newItem.front
        }

        override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCard = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(currentCard)
        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete Confirmation")
            builder.setMessage("Sure you want to delete this card?")
            builder.setPositiveButton("Delete") { dialogInterface, i ->
                holder.itemView.visibility = View.GONE
                holder.itemView.layoutParams.height = 0
                Toast.makeText(holder.itemView.context, "Card has been successfully deleted", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("<- No, go back"){dialogInterface, i ->
                dialogInterface.dismiss()
            }
            builder.show()
            true
        }

        holder.itemView.cb_card.setOnClickListener {
            currentCard.checked = !currentCard.checked
        }

//        holder.itemView?.btn_edit_delete?.setOnClickListener{
//            if(currentCard.checked == true){
//                it.visibility = View.GONE
//                it.layoutParams.height = 0
//            }
//        }

        holder.bind(currentCard)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [DataX]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [DataX]
     */
    class OnClickListener(val clickListener: (card:DataX) -> Unit) {
        fun onClick(card:DataX) = clickListener(card)
    }

}