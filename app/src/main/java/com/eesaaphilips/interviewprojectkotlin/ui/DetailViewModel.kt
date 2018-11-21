package com.eesaaphilips.interviewprojectkotlin.ui

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.eesaaphilips.interviewprojectkotlin.database.ProductDatabase
import com.eesaaphilips.interviewprojectkotlin.database.ProductRepository
import com.eesaaphilips.interviewprojectkotlin.model.Product
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlin.coroutines.CoroutineContext

/**
 * Description viewModel
 */
class DetailViewModel(application: android.app.Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    val liveProduct: MutableLiveData<Product> = MutableLiveData()
    private val repository: ProductRepository

    /**
     * initializer: instantiates the Dao and repository
     */
    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
    }

    /**
     * loads products by passed id and notifies liveData
     * @param id id of product
     */
    fun loadById(id: Int) = scope.launch(Dispatchers.IO) {
        liveProduct.postValue(repository.getById(id))
    }

    /**
     * ends background thread
     */
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}