package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}

/*
Interface `ItemDao` adalah Data Access Object (DAO) yang menyediakan metode untuk mengakses dan mengelola
data dalam tabel "items" menggunakan Room. DAO ini berisi berbagai operasi CRUD (Create, Read, Update, Delete)
dan query untuk mendapatkan data dari tabel "items". Berikut adalah penjelasan detail tentang setiap metode:

1. `@Insert(onConflict = OnConflictStrategy.IGNORE)`
   - Metode `insert(item: Item)` digunakan untuk menambahkan item baru ke dalam tabel.
   - `suspend` menandakan bahwa metode ini dirancang untuk digunakan dengan coroutine, sehingga eksekusi
     bisa dilakukan secara asinkron tanpa memblokir thread utama.
   - `OnConflictStrategy.IGNORE` berarti jika ada konflik (seperti item dengan kunci utama yang sama),
     operasi penyisipan akan diabaikan, dan data yang ada tetap dipertahankan.

2. `@Update`
   - Metode `update(item: Item)` memperbarui data item yang sudah ada dalam tabel.
   - Operasi ini cocok untuk memperbarui informasi item berdasarkan `id` yang sama.
   - `suspend` memungkinkan eksekusi asinkron menggunakan coroutine.

3. `@Delete`
   - Metode `delete(item: Item)` menghapus item yang diberikan dari tabel.
   - `suspend` memungkinkan operasi penghapusan dilakukan secara asinkron.

4. `@Query("SELECT * from items WHERE id = :id")`
   - Metode `getItem(id: Int)` mengambil item tertentu berdasarkan `id` yang diberikan.
   - Mengembalikan `Flow<Item>`, yang memungkinkan pengamatan perubahan data secara real-time.
   - `Flow` berguna untuk mendapatkan pembaruan data secara otomatis tanpa perlu menulis kode tambahan.

5. `@Query("SELECT * from items ORDER BY name ASC")`
   - Metode `getAllItems()` mengambil semua item dari tabel dan mengurutkannya berdasarkan nama dalam
     urutan abjad (ascending).
   - Mengembalikan `Flow<List<Item>>`, yang juga memungkinkan pengamatan perubahan data secara real-time.
   - Menggunakan `Flow` sangat membantu untuk membangun UI reaktif yang dapat memperbarui data secara otomatis.

DAO ini membantu Anda mengelola interaksi dengan tabel "items" dengan efisiensi dan mendukung
penggunaan coroutine untuk operasi basis data yang lebih aman di thread latar belakang.
*/
