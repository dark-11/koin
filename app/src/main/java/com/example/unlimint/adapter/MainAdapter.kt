package com.example.unlimint.adapter


import com.example.unlimint.utils.FixedQeue
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.unlimint.viewholder.JokeViewHolder

class MainAdapter(
    private val jokes: FixedQeue<String>
) : ListAdapter<String,JokeViewHolder>(JokeDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JokeViewHolder.inflate(parent)

    override fun getItemCount(): Int  {
        return jokes.size
    }
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) =  holder.bind(jokes[position])

    companion object {
        private object JokeDiff : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = false
            override fun areContentsTheSame(oldItem: String, newItem: String) = false
        }
    }
}