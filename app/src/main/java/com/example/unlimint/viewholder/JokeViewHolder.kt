package com.example.unlimint.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unlimint.databinding.ItemLayoutBinding

class JokeViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
           fun inflate (parent: ViewGroup) : JokeViewHolder {
               return JokeViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
           }
       }
        fun bind(item: String) {

            binding.joke.text = item
        }
    }
