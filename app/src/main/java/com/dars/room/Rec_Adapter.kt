package com.dars.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dars.room.databinding.RecyclerViewItemBinding
import com.dars.room.model.Note

class Rec_Adapter : ListAdapter<Note, Rec_Adapter.MyViewHolder>(diffUtill) {

    class MyViewHolder(val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var listner: OnItemClickListener? = null

    companion object {
        val diffUtill = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtTitle.text = item.title
        holder.binding.txtDescription.text = item.description
        holder.binding.btnDelete.setOnClickListener {
            listner?.delete(note = item)
        }
        holder.binding.btnUpdate.setOnClickListener {
            listner?.upadate(note = item)
        }
    }

    interface OnItemClickListener {
        fun delete(note: Note)
        fun upadate(note: Note)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        listner = onItemClickListener
    }
}