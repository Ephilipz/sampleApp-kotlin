package com.eesaaphilips.interviewprojectkotlin.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.eesaaphilips.interviewprojectkotlin.model.Product

/**
 * Database class
 */
@Database(entities = [Product::class], version = 1, exportSchema = true)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        /**
         * creates database using passed context
         * only one instance can be created at a time using synchronized
         *
         * @param context context to created database
         */
        fun getDatabase(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "products_db"
                )
                    .addCallback(ProductDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }


        /**
         * callback for database
         */
        private class ProductDatabaseCallback : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }
    }

}