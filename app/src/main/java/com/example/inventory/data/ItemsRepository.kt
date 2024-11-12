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

/*
 * Interface ItemsRepository mendefinisikan metode-metode yang akan digunakan
 * untuk mengelola data item dalam aplikasi. Ini adalah antarmuka yang mengatur
 * bagaimana aplikasi berinteraksi dengan sumber data.
 */

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    /*
     * getAllItemsStream() mengembalikan Flow dari daftar item untuk
     * mendapatkan semua item yang ada di database.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /*
     * getItemStream() mengembalikan Flow dari satu item berdasarkan id.
     * Aliran ini akan mengirimkan data null jika item dengan id tersebut tidak ditemukan.
     */
    fun getItemStream(id: Int): Flow<Item?>

    // Fungsi-fungsi di bawah ini adalah fungsi suspend yang
    // berarti  dapat dipanggil secara asinkronus
    // insertItem() adalah fungsi untuk menambahkan item baru ke dalam database.
    suspend fun insertItem(item: Item)

    // deleteItem() adalah fungsi untuk menghapus item dari database.
    suspend fun deleteItem(item: Item)

    // updateItem() adalah fungsi untuk memperbarui data item yang sudah ada di database.
    suspend fun updateItem(item: Item)
}
