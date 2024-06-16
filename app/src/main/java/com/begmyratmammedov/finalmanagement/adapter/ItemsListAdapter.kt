package com.begmyratmammedov.finalmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.begmyratmammedov.finalmanagement.R
import com.begmyratmammedov.finalmanagement.databinding.ItemBinding
import com.begmyratmammedov.finalmanagement.model.Item
import com.bumptech.glide.Glide

class ItemsListAdapter(private val context: Context): RecyclerView.Adapter<ItemsListAdapter.ItemListAdapterViewHolder>() {

    var onItemClick: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListAdapterViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListAdapterViewHolder(context, binding)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ItemListAdapterViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemListAdapterViewHolder(private val context: Context, private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                this.tTitle.text = item.name
                Glide.with(context).load(item.imageURL)
                    .dontAnimate()
                    .error(R.drawable.baseline_home_24)
                    .into(this.iIcon)
            }
        }
    }
}