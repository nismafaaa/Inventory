/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Kunci utama dengan auto-generasi nilai id, diatur ke 0 secara default.
    val name: String, // Nama item, disimpan sebagai string.
    val price: Double, // Harga item, diwakili dalam format angka desimal.
    val quantity: Int // Jumlah item, diwakili sebagai integer.
)

/*
Penjelasan:
1. `@Entity(tableName = "items")`
   - Anotasi ini mendeklarasikan bahwa `Item` adalah entitas Room dan akan dipetakan ke tabel bernama "items" di database.

2. `@PrimaryKey(autoGenerate = true)`
   - Properti `id` berfungsi sebagai kunci utama, dan Room akan secara otomatis menghasilkan nilai unik untuk `id` saat entitas baru dimasukkan.

3. Properti lainnya (`name`, `price`, `quantity`) merepresentasikan atribut dari item tersebut:
   - `name`: Menyimpan nama item.
   - `price`: Menyimpan harga item dengan tipe `Double` untuk menangani nilai desimal.
   - `quantity`: Menyimpan jumlah item dalam bentuk bilangan bulat.

Kelas ini mendukung penyimpanan dan pengambilan data menggunakan Room, mempermudah pengelolaan data berbasis SQLite.
*/