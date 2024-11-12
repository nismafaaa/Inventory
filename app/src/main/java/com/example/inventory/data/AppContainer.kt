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

import android.content.Context

/*
 * Interface AppContainer menentukan bahwa setiap kelas yang mengimplementasinya
 * harus menyediakan properti itemsRepository dari tipe ItemsRepository.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/*
 * Kelas AppDataContainer mengimplementasikan interface AppContainer
 * dan mengatur akses ke itemsRepository dengan memanfaatkan database.
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /*
     * Properti itemsRepository diinisialisasi dengan "lazy", sehingga hanya dibuat
     * saat pertama kali digunakan.
     */
    override val itemsRepository: ItemsRepository by lazy {
        /*
         * OfflineItemsRepository dibuat dengan mengambil data dari database lokal,
         * yang diakses melalui InventoryDatabase dan itemDao().
         */
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

