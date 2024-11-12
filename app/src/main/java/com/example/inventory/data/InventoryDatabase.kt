package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
 * Menggunakan anotasi @Database untuk mendefinisikan kelas sebagai database Room,
 * dan menentukan entitas (table) Item serta versi databasenya.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /*
     * Mendefinisikan fungsi abstract itemDao(), yang akan mengakses
     * objek DAO (Data Access Object) untuk entitas Item.
     */
    abstract fun itemDao(): ItemDao

    companion object {
        /*
         * @Volatile memastikan bahwa setiap perubahan pada Instance segera terlihat oleh semua thread lainnya.
         */
        @Volatile
        private var Instance: InventoryDatabase? = null

        /*
         * getDatabase() adalah fungsi yang mengembalikan instance dari InventoryDatabase.
         * Jika instance sudah ada, langsung dikembalikan. Jika belum, maka dibuat instance baru.
         */
        fun getDatabase(context: Context): InventoryDatabase {
            // Mengecek apakah Instance sudah ada, jika tidak, membuat database baru
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    "item_database"
                )
                    .build()
                    .also { Instance = it } // Menyimpan instance baru ke variabel Instance
            }
        }
    }
}