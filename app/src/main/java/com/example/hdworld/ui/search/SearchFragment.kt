package com.example.hdworld.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hdworld.R
import com.example.hdworld.model.PhotoData
import com.example.hdworld.view.PhotoDetails
import com.example.hdworld.view.PhotoListAdapter
import com.example.hdworld.viewmodel.SearchListViewModel

class NotificationsFragment : Fragment(), PhotoListAdapter.OnPhotoItemClickListner {

    private lateinit var notificationsViewModel: NotificationsViewModel

    lateinit var viewModel: SearchListViewModel
    private val PhotosAdapter = PhotoListAdapter(arrayListOf(), this)
    lateinit var photosList: RecyclerView
    lateinit var list_errors: TextView
    lateinit var list_welcome: TextView
    lateinit var loading_view2: ProgressBar
    lateinit var queries: String
    //var queries: String = "cat"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        viewModel = ViewModelProviders.of(this).get(SearchListViewModel::class.java)

        viewModel.refresh()

        photosList = root.findViewById(R.id.searchList)
        list_errors = root.findViewById(R.id.list_errors)
        list_welcome = root.findViewById(R.id.welcome)
        loading_view2 = root.findViewById(R.id.loading_view2)


        //for making the search button
        setHasOptionsMenu(true)

        photosList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PhotosAdapter
        }

        observeViewModel()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //telling the user that the process is working
                Toast.makeText(context, "Searching for $query", Toast.LENGTH_LONG).show()
                //viewModel.x = "$query"
                //queries = "$query"

                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.Photos.observe(viewLifecycleOwner, { photos ->
            photos?.let {
                photosList.visibility = View.VISIBLE
                list_welcome.visibility = View.GONE
                PhotosAdapter.updatePhotos(it)
            }
        })

        viewModel.PhotoLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                list_errors.visibility = if (it) View.VISIBLE else View.GONE
                list_welcome.visibility = View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                loading_view2.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_errors.visibility = View.GONE
                    photosList.visibility = View.GONE
                    list_welcome.visibility = View.GONE
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