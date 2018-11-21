package com.eesaaphilips.interviewprojectkotlin.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eesaaphilips.interviewprojectkotlin.R
import com.eesaaphilips.interviewprojectkotlin.model.Product
import com.eesaaphilips.interviewprojectkotlin.ui.DescriptionActivity

class ProductListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<Product>()
    private val mContext = context

    inner class ProductViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val title: TextView = itemview.findViewById(R.id.name_tv)
        val price: TextView = itemview.findViewById(R.id.price_tv)
        val image: ImageView = itemview.findViewById(R.id.image_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemview = inflater.inflate(R.layout.product_row, parent, false)
        return ProductViewHolder(itemview)
    }

    override fun onBindViewHolder(p0: ProductViewHolder, p1: Int) {
        if (!products.isEmpty()) {
            val currentProduct = products[p1]
            p0.title.text = currentProduct.name
            p0.price.text = "$${currentProduct.price}"
            Glide.with(mContext).load(currentProduct.imageUrl).into(p0.image)
            p0.itemView.setOnClickListener {
                startDescriptionActivity(currentProduct)
            }
        } else {
            Toast.makeText(mContext, "Error binding products", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startDescriptionActivity(product: Product) {
        val intent =
            Intent(mContext, DescriptionActivity::class.java).putExtra(DescriptionActivity.DESCR_PROD_ID, product.id)
        mContext.startActivity(intent)
    }

    internal fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = products.size

}