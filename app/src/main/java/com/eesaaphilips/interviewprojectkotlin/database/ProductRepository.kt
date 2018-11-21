package com.eesaaphilips.interviewprojectkotlin.database

import android.arch.lifecycle.LiveData
import com.eesaaphilips.interviewprojectkotlin.model.Product

/**
 * Product Repository: manages the dao functions such as inserting and retrieving products
 */
class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    /**
     * inserts product into database via the Dao class
     *
     * @param product product to be inserted
     */
    fun insert(product: Product) {
        productDao.insert(product)
    }

    /**
     * gets product using the id via the Dao class
     *
     * @param id the id used to retrieve the product from the Dao
     */
    fun getById(id: Int): Product = productDao.getBydId(id)
}