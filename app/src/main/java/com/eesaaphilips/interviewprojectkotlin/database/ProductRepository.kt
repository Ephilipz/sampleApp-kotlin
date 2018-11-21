package com.eesaaphilips.interviewprojectkotlin.database

import android.arch.lifecycle.LiveData
import com.eesaaphilips.interviewprojectkotlin.model.Product
import java.util.concurrent.Executors

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    fun insert(product: Product) {
        productDao.insert(product)
    }

    fun getById(id: Int): Product = productDao.getBydId(id)
}