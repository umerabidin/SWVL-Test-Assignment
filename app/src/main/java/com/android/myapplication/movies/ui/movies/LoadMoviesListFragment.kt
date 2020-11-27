package com.android.myapplication.movies.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FragmentLoadMoviesListBinding
import com.android.myapplication.movies.listener.RecyclerViewItemClickListener
import com.android.myapplication.movies.models.read_movies.Movy
import org.koin.androidx.viewmodel.ext.android.viewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoadMoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadMoviesListFragment : Fragment(), RecyclerViewItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val moviesViewModel by viewModel<LoadMoviesViewModel>()
    lateinit var mAdapter: FlickerMoviesRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onMovieClick(movie: Movy)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    lateinit var binding: FragmentLoadMoviesListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_load_movies_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = FlickerMoviesRecyclerAdapter(moviesViewModel.getMovies(), this)

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerview.getContext(),
                    LinearLayoutManager(activity).getOrientation()
                )
            )
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoadMoviesListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoadMoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(any: Any) {
        val movy: Movy = any as Movy
        callbacks?.onMovieClick(movy)
    }

    fun onQueryTextChange(newText: String?) {
        mAdapter.filter.filter(newText)
    }


}