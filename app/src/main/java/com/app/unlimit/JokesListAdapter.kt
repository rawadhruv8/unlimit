package com.app.unlimit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.unlimit.databinding.AdapterJokesItemBinding
import com.app.unlimit.retrofit.ResponseJoke

class JokesListAdapter(
    private val list: ArrayList<ResponseJoke>
) :
    RecyclerView.Adapter<JokesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterJokesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.tvJoke.text = data.joke
    }

    fun notifySingleItem(joke: ResponseJoke) {
        list.add(joke)

        if(list.size >= 10) {
            list.removeAt(0)
            notifyItemRemoved(0)
        }

        notifyItemInserted(list.size)
    }



    fun notifyLocalList(jokes : ArrayList<ResponseJoke>) {
        list.addAll(jokes)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: AdapterJokesItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}