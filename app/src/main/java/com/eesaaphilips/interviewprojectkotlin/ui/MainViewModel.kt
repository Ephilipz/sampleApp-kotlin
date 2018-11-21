package com.eesaaphilips.interviewprojectkotlin.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.eesaaphilips.interviewprojectkotlin.database.ProductDatabase
import com.eesaaphilips.interviewprojectkotlin.database.ProductRepository
import com.eesaaphilips.interviewprojectkotlin.model.Product
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlin.coroutines.CoroutineContext

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = ProductDatabase.getDatabase(application, scope).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
    }

    fun insert(product: Product) = scope.launch(Dispatchers.IO) {
        repository.insert(product)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}