package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }
}

/*
Kelas `InventoryDatabase` adalah kelas abstrak yang memperluas `RoomDatabase` dan berfungsi sebagai
titik akses utama ke database SQLite yang dikelola oleh Room. Kelas ini mengelola pembuatan dan
pengelolaan database serta menyediakan metode untuk mendapatkan DAO. Berikut adalah penjelasan
detail tentang elemen-elemen yang ada:

1. `@Database(entities = [Item::class], version = 1, exportSchema = false)`
   - Anotasi `@Database` mendeklarasikan bahwa kelas ini adalah database Room.
   - `entities = [Item::class]`: Menentukan bahwa entitas `Item` akan digunakan untuk membuat tabel dalam database.
   - `version = 1`: Menentukan versi database, yang digunakan untuk migrasi database di masa mendatang.
   - `exportSchema = false`: Menentukan apakah skema database akan diekspor (berguna untuk tujuan dokumentasi dan migrasi).

2. `abstract class InventoryDatabase : RoomDatabase()`
   - Kelas abstrak yang memperluas `RoomDatabase`. Kelas ini tidak dapat diinstansiasi langsung
     dan hanya diakses melalui metode `getDatabase`.

3. `abstract fun itemDao(): ItemDao`
   - Metode abstrak yang mengembalikan `ItemDao`. Ini digunakan oleh Room untuk menyediakan
     implementasi DAO secara otomatis saat database dibuat.

4. `companion object`
   - Digunakan untuk membuat instance tunggal (singleton) dari `InventoryDatabase`.
   - `@Volatile` menandai `Instance` sebagai variabel yang dapat diakses dengan aman oleh beberapa thread.
     - Ini memastikan bahwa perubahan pada `Instance` segera terlihat oleh semua thread.

5. `fun getDatabase(context: Context): InventoryDatabase`
   - Fungsi ini menyediakan cara untuk mendapatkan instance `InventoryDatabase`.
   - Menggunakan pola singleton untuk memastikan hanya satu instance database yang dibuat di seluruh aplikasi.
   - `synchronized(this)`: Blok sinkronisasi digunakan untuk mencegah beberapa thread membuat lebih dari satu
     instance database pada saat yang sama.
   - `Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")`
     - `Room.databaseBuilder`: Membuat atau mendapatkan instance `InventoryDatabase`.
     - `context`: Memberikan konteks aplikasi untuk pembuatan database.
     - `InventoryDatabase::class.java`: Kelas database yang digunakan oleh Room.
     - `"item_database"`: Nama file database.

Dengan ini, Anda memiliki database Room yang efisien dengan metode akses DAO dan pengaturan yang
mendukung thread-safe instansiasi.
*/
