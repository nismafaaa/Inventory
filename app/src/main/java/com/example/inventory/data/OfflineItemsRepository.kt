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

/*
 * OfflineItemsRepository adalah implementasi dari interface ItemsRepository.
 * Repository ini menggunakan itemDao untuk mengakses database secara offline.
 */
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {

    /*
     * getAllItemsStream() mengembalikan Flow dari semua item di database,
     * memanggil metode getAllItems() dari itemDao untuk mendapatkan data.
     */
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    /*
     * getItemStream() mengembalikan Flow dari item berdasarkan id yang diberikan,
     * memanggil metode getItem() dari itemDao.
     */
    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    /*
     * insertItem() adalah fungsi suspend yang menambahkan item baru ke database
     * dengan memanggil metode insert() dari itemDao.
     */
    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    /*
     * deleteItem() adalah fungsi suspend yang menghapus item dari database
     * dengan memanggil metode delete() dari itemDao.
     */
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /*
     * updateItem() adalah fungsi suspend yang memperbarui data item di database
     * dengan memanggil metode update() dari itemDao.
     */
    override suspend fun updateItem(item: Item) = itemDao.update(item)
}


