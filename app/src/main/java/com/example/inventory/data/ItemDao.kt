package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
 * @Dao menandai interface ini sebagai Data Access Object (DAO) untuk entitas Item,
 * yang menyediakan metode untuk mengakses data di database.
 */
@Dao
interface ItemDao {

    /*
     * insert() adalah fungsi untuk menambahkan item baru ke dalam tabel "items".
     * onConflict = OnConflictStrategy.IGNORE berarti data baru diabaikan jika
     * sudah ada data dengan id yang sama.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    // update() adalah fungsi untuk memperbarui data item yang ada di tabel "items".
    @Update
    suspend fun update(item: Item)

    // delete() adalah fungsi untuk menghapus data item dari tabel "items".
    @Delete
    suspend fun delete(item: Item)

    /*
     * getItem() adalah fungsi untuk mengambil satu item dari tabel "items"
     * berdasarkan id yang diberikan. Hasilnya berupa Flow, sehingga dapat diobservasi
     * secara real-time.
     */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /*
     * getAllItems() adalah fungsi untuk mengambil semua item dari tabel "items",
     * diurutkan berdasarkan nama dalam urutan naik (ASC). Lalu dikembalikan melalui
     * Flow dengan daftar item untuk pemantauan data secara real-time.
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}