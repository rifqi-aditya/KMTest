package com.rifqi.kmtest.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rifqi.kmtest.data.source.remote.response.DataItem
import com.rifqi.kmtest.databinding.ItemUserBinding
import com.rifqi.kmtest.ui.second.SecondScreenActivity

class UserAdapter : PagingDataAdapter<DataItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SecondScreenActivity::class.java)
            intent.putExtra("SELECTED_USER_NAME", item?.let { "${it.firstName} ${it.lastName}" })
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem?) {
            if (item != null) {
                Glide.with(itemView.context)
                    .load(item.avatar)
                    .into(binding.ivUserImage)
                binding.tvFullName.text = item.let { "${it.firstName} ${it.lastName}" }
                binding.tvEmail.text = item.email
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}