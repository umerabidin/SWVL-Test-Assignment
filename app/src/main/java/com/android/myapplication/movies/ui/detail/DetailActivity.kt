package com.android.myapplication.movies.ui.detail

import PAGE_CAST
import PAGE_COUNT
import PAGE_GENRE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.ActivityDetailBinding
import com.android.myapplication.movies.models.read_movies.Movy
import com.android.myapplication.movies.ui.detail.fragments.DetailFragmentViewModel
import com.android.myapplication.movies.ui.movies.fragments.CastFragment
import com.android.myapplication.movies.ui.movies.fragments.GenresFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


private const val INTENT_EXTRA_MOVIE = "movie"

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailFragmentViewModel by viewModel {

        parametersOf(
            Gson().fromJson(intent?.extras?.getString(INTENT_EXTRA_MOVIE), Movy::class.java)
        )
    }
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val mAdapter = SlideImagesAdapter(ArrayList())



        binding.images.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }

        viewModel.movieDetails.observe(this, Observer {
            if (it.data != null) {
                it.data
                mAdapter.updatePhotos(it.data.photo)
            }

        })
        initViewPager()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMovieDetails(1)
    }


    fun initViewPager() {
        viewPager = binding.contentDetail.viewpager
        tabLayout = binding.contentDetail.tabs
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return PAGE_COUNT
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    PAGE_GENRE -> GenresFragment.newInstance(viewModel.movie.genres!!, "")

                    PAGE_CAST -> CastFragment.newInstance(viewModel.movie.cast!!, "")
                    else -> throw IndexOutOfBoundsException()
                }
            }

        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PAGE_GENRE -> "Genre"
            PAGE_CAST -> "Casts"
            else -> null
        }
    }


    companion object {
        private const val TAG = "DetailActivity"
        fun getIntent(movie: Movy, context: Context): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_MOVIE, Gson().toJson(movie))
            return intent
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cancelRequest()
    }
}
