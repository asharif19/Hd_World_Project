package com.example.hdworld.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hdworld.R
import com.kc.unsplash.Unsplash
import com.kc.unsplash.api.Scope
import com.kc.unsplash.models.User

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var Usernametext: TextView
    lateinit var Usernameinput : EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        Usernametext = root.findViewById(R.id.UsernameTxt)
        Usernameinput = root.findViewById(R.id.editTextTextPersonName)
        val getprofilebtn = root.findViewById<Button>(R.id.EditprofileBtn)
        getprofilebtn.setOnClickListener {
            val token =
                "https://unsplash.com/oauth/client_id=wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ/client_secret=p93UkJVfpB-y1o4dh5-LTkwheY_ISCj-xOOpPI_SFaA"
            val unsplash = Unsplash("wFgDXxW091tf9AzP5FIgF0tRdkblXx7plNcYY5babaQ", token)

            val scopes = listOf(Scope.PUBLIC, Scope.READ_USER, Scope.WRITE_USER)
            unsplash.authorize(requireContext(), "urn:ietf:wg:oauth:2.0:oob", scopes)

            unsplash.getToken(
                "p93UkJVfpB-y1o4dh5-LTkwheY_ISCj-xOOpPI_SFaA",
                "urn:ietf:wg:oauth:2.0:oob",
                "3EbAEec-HhQexd7YBlw-u5w2XL9ydDNGezD4GYYV8MQ",
                {
                    Log.d("Token", it.accessToken)
                },
                {
                    Log.d("Token", it)
                }
            )
            unsplash.setToken(token)
        }
        //unsplash.users.getPortfolio()

        /*getprofilebtn.setOnClickListener {
            unsplash.users.getByUsername(Usernameinput.toString(),
                onComplete = {
                    Usernametext.text = User
                    Toast.makeText(requireContext(), "Success in Finding $it", Toast.LENGTH_LONG)
                    .show()
                    Log.d("User", "User Found $it")
                },
                onError = {
                    Toast.makeText(requireContext(), "Error in Finding $it", Toast.LENGTH_LONG)
                        .show()
                    Log.d("Error in Finding", it)
                })
        }*/
        return root
    }
}