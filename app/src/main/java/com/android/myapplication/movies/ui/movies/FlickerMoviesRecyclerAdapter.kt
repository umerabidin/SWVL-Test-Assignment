package com.android.myapplication.movies.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FlickerMoviesListItemBinding
import com.android.myapplication.movies.listener.RecyclerViewItemClickListener
import com.android.myapplication.movies.models.read_movies.Movy
import java.util.*
import kotlin.collections.ArrayList

class FlickerMoviesRecyclerAdapter(
    val movies: List<Movy>,
    val clickListener: RecyclerViewItemClickListener
) :
    RecyclerView.Adapter<FlickerMoviesRecyclerAdapter.VH>(),Filterable {

    var moviesFilterList = ArrayList<Movy>()

    init {
        moviesFilterList = movies as ArrayList<Movy>
    }
    class VH(itemView: FlickerMoviesListItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding: FlickerMoviesListItemBinding

        init {
            binding = itemView
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: FlickerMoviesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.flicker_movies_list_item, parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.movieTitle.text = moviesFilterList[position].title
        holder.binding.year.text = moviesFilterList[position].year.toString()
        holder.binding.ratingTitle.text = moviesFilterList[position].rating.toString()
        holder.binding.cvItem.setOnClickListener {
            clickListener.onItemClick(moviesFilterList[position])
        }
    }

    override fun getItemCount() = moviesFilterList.size
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    moviesFilterList = movies as ArrayList<Movy>
                } else {
                    val resultList = ArrayList<Movy>()
                    for (row in movies) {
                        if (row.title?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT)) == true) {
                            resultList.add(row)
                        }
                    }
                    moviesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFilterList = results?.values as ArrayList<Movy>
                notifyDataSetChanged()
            }

        }
    }
}