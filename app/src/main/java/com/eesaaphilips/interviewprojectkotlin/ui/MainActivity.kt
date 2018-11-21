package com.eesaaphilips.interviewprojectkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.eesaaphilips.interviewprojectkotlin.R
import com.eesaaphilips.interviewprojectkotlin.adapters.ProductListAdapter
import com.eesaaphilips.interviewprojectkotlin.model.Product
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var requestQueue: RequestQueue
    private val URL_JSON = "https://limitless-forest-98976.herokuapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val viewAdapter = ProductListAdapter(this)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        parseJSON()

        viewModel.allProducts.observe(this, Observer { products ->
            products?.let { viewAdapter.setProducts(it) }
        })

    }

    private fun parseJSON() {
        val request = JsonObjectRequest(Request.Method.GET, URL_JSON, null, Response.Listener { response ->
            //successfully connected to host
            try {
                val jsonArray = response.getJSONArray("data")

                for (i in 0 until jsonArray.length()) {
                    val data = jsonArray.getJSONObject(i)
                    val id = data.getInt("id")
                    val name = data.getString("name")
                    val description = data.getString("productDescription")
                    val price = data.get("price") as Int
                    val imageObject = data.getJSONObject("image")
                    val imageURL = imageObject.getString("link").replace("http", "https")
                    val width = imageObject.getInt("width")
                    val height = imageObject.getInt("height")

                    //create new Product using the information
                    val product = Product(id, name, description, price, imageURL, width, height)

                    //insert the product into the database using the viewmodel
                    viewModel.insert(product)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            //error connecting to host
            error.printStackTrace()
        })

        //process request using the requestQue variable through Volley.newRequestQue
        requestQueue = Volley.newRequestQueue(this@MainActivity)
        requestQueue.add(request)
    }

}