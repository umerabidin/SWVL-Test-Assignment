package com.android.myapplication.movies.ui.detail

import PAGE_CAST
import PAGE_GENRE
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.ActivityDetailBinding
import com.android.myapplication.movies.models.read_movies.Movy
import com.android.myapplication.movies.ui.detail.fragments.DetailFragmentViewModel
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
    var count = 1
    lateinit var progress: ProgressDialog

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })


        val mAdapter = SlideImagesAdapter(ArrayList())



        binding.images.apply {
            layoutManager =
                GridLayoutManager(this@DetailActivity, 2)
            adapter = mAdapter
        }
        binding.movieName.text = viewModel.movie.title
        binding.movieYear.text = viewModel.movie.year.toString()
        val genreBuilder = StringBuilder()
        if (viewModel.movie.genres != null && viewModel.movie.genres?.size!! > 0) {
            viewModel.movie.genres?.forEach({
                genreBuilder.append(it)
                genreBuilder.append("\n")
            }

            )
            binding.genres.text = genreBuilder
        }

        val castBuilder = StringBuilder()
        if (viewModel.movie.cast != null && viewModel.movie.cast?.size!! > 0) {
            viewModel.movie.cast?.forEach({
                castBuilder.append(it)
                castBuilder.append("\n")
            }

            )
            binding.caste.text = castBuilder
        }


        viewModel.movieDetails.observe(this, Observer {
            showLoading(false)
            if (it.data != null) {
                it.data
                mAdapter.updatePhotos(it.data.photo)
            }

        })


        binding.images.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val layoutManager = binding.images.getLayoutManager() as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        showLoading(true)
                        count++
                        viewModel.getMovieDetails(count)

                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        showLoading(true)

        viewModel.getMovieDetails(count)
    }


//    fun initViewPager() {
//
//        viewPager.adapter = object : FragmentStateAdapter(this) {
//            override fun getItemCount(): Int {
//                return PAGE_COUNT
//            }
//
//            override fun createFragment(position: Int): Fragment {
//                return when (position) {
//                    PAGE_GENRE -> GenresFragment.newInstance(viewModel.movie.genres!!, "")
//
//                    PAGE_CAST -> CastFragment.newInstance(viewModel.movie.cast!!, "")
//                    else -> throw IndexOutOfBoundsException()
//                }
//            }
//
//        }
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = getTabTitle(position)
//        }.attach()
//    }

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


    fun showLoading(doLoading: Boolean) {
        if (doLoading) {
            progress = ProgressDialog(this, R.style.MyAlertDialogStyle)
            progress.setTitle("Loading")
            progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
            progress.show()
        } else if (::progress.isInitialized && progress.isShowing) {
            progress.dismiss()
        }
    }
}
