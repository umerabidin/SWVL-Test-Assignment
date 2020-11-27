package com.android.myapplication.movies.ui.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FragmentGenresBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GenresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenresFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<String> = ArrayList()
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getStringArrayList(ARG_PARAM1) as ArrayList<String>
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentGenresBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_genres, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsAdapter: ArrayAdapter<String>? =
            activity?.let { ArrayAdapter(it, R.layout.mytextview, param1) }
        binding.genreList.adapter = itemsAdapter
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: ArrayList<String>, param2: String) =
            GenresFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}