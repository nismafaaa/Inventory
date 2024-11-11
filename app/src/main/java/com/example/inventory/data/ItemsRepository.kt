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

import kotlinx.coroutines.flow.Flow

/**
 * Interface `ItemsRepository` adalah repository yang menyediakan metode untuk operasi
 * insert, update, delete, dan retrieve (mengambil data) dari sumber data yang diberikan.
 * Repository ini bertindak sebagai lapisan abstraksi antara sumber data (misalnya, database Room)
 * dan komponen lain dalam aplikasi Anda, seperti ViewModel. Penjelasan rinci dari setiap metode
 * dalam interface ini adalah sebagai berikut:
 */
interface ItemsRepository {
    /**
     * Mengambil semua item dari sumber data dalam bentuk `Flow<List<Item>>`.
     * - `Flow<List<Item>>`: Mengembalikan daftar item secara real-time dan memungkinkan
     *   pengamatan perubahan data secara otomatis.
     * - Cocok untuk UI reaktif, di mana perubahan data langsung tercermin di tampilan.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Mengambil satu item dari sumber data yang sesuai dengan `id` yang diberikan.
     * - `Flow<Item?>`: Mengembalikan `Flow` dari `Item` atau `null` jika item tidak ditemukan.
     * - Berguna untuk mendapatkan dan menampilkan detail spesifik dari item yang dipilih.
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Menyisipkan item ke dalam sumber data.
     * - `suspend`: Metode ini dirancang untuk digunakan dengan coroutine agar dapat dijalankan
     *   secara asinkron tanpa memblokir thread utama.
     */
    suspend fun insertItem(item: Item)

    /**
     * Menghapus item dari sumber data.
     * - `suspend`: Operasi ini dilakukan secara asinkron untuk menghindari pemblokiran thread utama.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Memperbarui item di sumber data.
     * - `suspend`: Operasi ini dilakukan secara asinkron, memungkinkan performa yang lebih baik
     *   dalam aplikasi yang responsif.
     */
    suspend fun updateItem(item: Item)
}

/*
Repository ini memisahkan logika data dari logika UI, membuat kode lebih modular dan lebih mudah
untuk diuji. Dengan menggunakan `Flow`, aplikasi Anda dapat menangani data secara reaktif,
yang sangat ideal untuk aplikasi berbasis Kotlin.
*/