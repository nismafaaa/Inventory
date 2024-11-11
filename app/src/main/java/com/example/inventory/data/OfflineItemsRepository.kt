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

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)
}

/*
Kelas `OfflineItemsRepository` adalah implementasi dari interface `ItemsRepository` yang
menggunakan `ItemDao` untuk mengakses dan mengelola data dalam database Room. Kelas ini
menangani semua operasi data secara offline, menggunakan Room sebagai sumber data lokal.
Berikut adalah penjelasan rinci tentang implementasi ini:

1. `class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository`
   - Kelas `OfflineItemsRepository` mengimplementasikan interface `ItemsRepository`.
   - `itemDao`: Objek DAO (Data Access Object) yang digunakan untuk menjalankan operasi database.
   - Kelas ini menerima `itemDao` sebagai parameter konstruktor dan menggunakannya untuk berinteraksi
     dengan database Room.

2. `override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()`
   - Metode ini mengembalikan aliran (`Flow`) dari daftar semua item di database, seperti yang diambil
     oleh `ItemDao`.
   - `Flow<List<Item>>` memungkinkan pengamatan perubahan data secara real-time.

3. `override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)`
   - Metode ini mengembalikan aliran (`Flow`) dari item tertentu berdasarkan `id`.
   - `Flow<Item?>` mengembalikan item atau `null` jika item dengan `id` tersebut tidak ditemukan.

4. `override suspend fun insertItem(item: Item) = itemDao.insert(item)`
   - Metode ini menyisipkan item baru ke dalam database, menggunakan `ItemDao`.
   - `suspend` memungkinkan metode ini dieksekusi secara asinkron dengan coroutine, memastikan
     bahwa operasi basis data tidak memblokir thread utama.

5. `override suspend fun deleteItem(item: Item) = itemDao.delete(item)`
   - Metode ini menghapus item dari database.
   - Menggunakan kata kunci `suspend` untuk mendukung eksekusi asinkron.

6. `override suspend fun updateItem(item: Item) = itemDao.update(item)`
   - Metode ini memperbarui data item yang ada di database.
   - Juga menggunakan `suspend` untuk operasi asinkron, menjaga performa aplikasi.

Kelas `OfflineItemsRepository` memanfaatkan Room dan DAO untuk menyederhanakan operasi basis data.
Dengan memisahkan logika akses data dari komponen lain, kelas ini membuat kode lebih terorganisir dan
mudah diuji. Penggunaan coroutine dengan kata kunci `suspend` membantu menjaga responsivitas UI.
*/
