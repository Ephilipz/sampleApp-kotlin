package com.eesaaphilips.interviewprojectkotlin

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.eesaaphilips.interviewprojectkotlin.database.ProductDao
import com.eesaaphilips.interviewprojectkotlin.database.ProductDatabase
import com.eesaaphilips.interviewprojectkotlin.model.Product
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

/**
 * Tests room database by creating and retrieving products then comparing them
 */

@RunWith(AndroidJUnit4::class)
class RoomTests {
    private lateinit var productDao: ProductDao
    private lateinit var db: ProductDatabase

    /**
     * instantiates the ProductDatabase and ProductDao
     */
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getContext()
        db = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java).build()
        productDao = db.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    /**
     * Adds a product to the database and retrieves it
     */
    @Test
    @Throws(Exception::class)
    fun writeProductAndRead() {
        val product = Product(
            2,
            "Fifa 19",
            "playstation cd for fifa 19",
            60,
            "https://images-na.ssl-images-amazon.com/images/I/8164ScXLNCL._SX342_.jpg",
            342,
            426
        )
        productDao.insert(product)
        val productById = productDao.getBydId(2)
        assert(productById.name == product.name)
    }
}