package com.eesaaphilips.interviewprojectkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.eesaaphilips.interviewprojectkotlin.R

/**
 * Description Activity
 */
class DescriptionActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var viewModel: DetailViewModel
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        name = findViewById(R.id.name_tv)
        price = findViewById(R.id.price_tv)
        image = findViewById(R.id.image)
        description = findViewById(R.id.description_tv)
        fab = findViewById(R.id.back_fab)

        fab.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val id = intent.getIntExtra(DESCR_PROD_ID, -1)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        if (id != -1) {
            //id passed
            viewModel.loadById(id)
            viewModel.liveProduct.observe(this, Observer { product ->
                name.text = product!!.name
                price.text = "$${product.price}"
                Glide.with(this).load(product.imageUrl).into(image)
                description.text = product.description
                title = product.name

            })
        } else {
            Toast.makeText(this, "Unable to receive data", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * constant values used to pass data to intent
     */
    companion object {
        const val DESCR_PROD_ID = "product_id"
    }
}
