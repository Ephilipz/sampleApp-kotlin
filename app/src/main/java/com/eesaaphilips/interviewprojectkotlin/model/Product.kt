package com.eesaaphilips.interviewprojectkotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Product data class
 */
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey val id: Int, val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val width: Int,
    val height: Int
)