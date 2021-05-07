package com.example.hdworld.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hdworld.R
import com.example.hdworld.model.PhotoData
import com.example.hdworld.view.PhotoDetails
import com.example.hdworld.view.PhotoListAdapter
import com.example.hdworld.viewmodel.ListViewModel
import com.kc.unsplash.Unsplash
import com.kc.unsplash.models.Photo
import com.squareup.picasso.Picasso

class HomeFragment : Fragment(), PhotoListAdapter.OnPhotoItemClickListner{

    private lateinit var homeViewModel: HomeViewModel

    lateinit var viewModel: ListViewModel
    lateinit var photosss: List<Photo>
    private val PhotosAdapter = PhotoListAdapter(arrayListOf(), this);
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var photosList: RecyclerView
    lateinit var list_error: TextView
    lateinit var loading_view: ProgressBar
    val x = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        photosList = root.findViewById<RecyclerView>(R.id.photosList)
        list_error = root.findViewById<TextView>(R.id.list_error)
        loading_view = root.findViewById<ProgressBar>(R.id.loading_view)

        photosList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PhotosAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)

        val unsplash = Unsplash(
            "wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ",
            "https://unsplash.com/oauth/client_id=wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ/client_secret=p93UkJVfpB-y1o4dh5-LTkwheY_ISCj-xOOpPI_SFaA"
        )
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {

                    unsplash.photos.search(query,
                        onComplete = {
                            Log.d("Photos", "Total Results Found " + it.total!!)
                            val photos = it.results
                        },
                        onError = {
                            Log.d("Unsplash", it)
                        }
                    )
                    Toast.makeText(context, "Searching for $query", Toast.LENGTH_LONG).show()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun observeViewModel() {
        viewModel.Photos.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                photosList.visibility = View.VISIBLE
                PhotosAdapter.updatePhotos(it)
            }
        })

        viewModel.PhotoLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    photosList.visibility = View.GONE
                }
            }
        })
    }

    override fun onItemClick(photo: PhotoData, position: Int) {
        //Toast.makeText(context, photo.Likes , Toast.LENGTH_SHORT).show()

        val intent = Intent(activity, PhotoDetails::class.java)

        intent.putExtra("likes", photo.Likes)
        intent.putExtra("name", photo.user?.name)
        intent.putExtra("location", photo.user?.location)
        intent.putExtra("urls", photo.urls?.regular)
        intent.putExtra("alt_description", photo.Alt_description)
        startActivity(intent)
    }

}