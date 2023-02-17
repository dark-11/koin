package com.example.unlimint.adapter


import com.example.unlimint.utils.FixedQeue
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unlimint.viewholder.JokeViewHolder

class MainAdapter(
    private val jokes: FixedQeue<String>
) : RecyclerView.Adapter<JokeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JokeViewHolder.inflate(parent)

    override fun getItemCount(): Int  {
        return jokes.size
    }
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) =  holder.bind(jokes[position])
}