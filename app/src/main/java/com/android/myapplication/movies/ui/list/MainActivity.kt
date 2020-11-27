package com.android.myapplication.movies.ui.list

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import com.android.myapplication.movies.R
import com.android.myapplication.movies.models.read_movies.Movy
import com.android.myapplication.movies.ui.detail.DetailActivity
import com.android.myapplication.movies.ui.movies.LoadMoviesListFragment


class MainActivity : AppCompatActivity(),
    LoadMoviesListFragment.Callbacks, SearchView.OnQueryTextListener {
    lateinit var fragment: LoadMoviesListFragment
    private lateinit var toolbar: Toolbar

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        val isFragmentContainerEmpty = savedInstanceState == null

        fragment = LoadMoviesListFragment()
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.main_container,
                    fragment
                )
                .commit()
        }
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        (supportFragmentManager.findFragmentById(R.id.main_container) as LoadMoviesListFragment).onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fragment_movie_list_menu, menu)

        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        val searchView: SearchView =
            MenuItemCompat.getActionView(menu.findItem(R.id.menu_item_search)) as SearchView
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMovieClick(movie: Movy) {
        val intent = DetailActivity.getIntent(movie, this)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        fragment.onQueryTextChange(newText)
        return true
    }

}
