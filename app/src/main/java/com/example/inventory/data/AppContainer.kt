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

/**
 * Interface `AppContainer` berfungsi sebagai kontainer aplikasi untuk Dependency Injection.
 * Dependency Injection (DI) digunakan untuk mengelola dependensi dengan cara yang efisien dan terorganisir,
 * memungkinkan komponen-komponen aplikasi Anda untuk lebih mudah diuji dan dikelola.
 * Interface ini mendefinisikan properti `itemsRepository` yang diimplementasikan dalam kelas-kelas turunannya.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * Implementasi `AppContainer` yang menyediakan instance dari `OfflineItemsRepository`.
 * `AppDataContainer` digunakan untuk mengelola dan menyediakan dependensi yang diperlukan,
 * khususnya instance `ItemsRepository`.
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementasi `itemsRepository` yang dibuat secara lazy (malas).
     * - `by lazy`: Inisialisasi `itemsRepository` hanya akan dilakukan saat pertama kali diakses,
     *   memastikan bahwa instance hanya dibuat satu kali dan menghemat sumber daya.
     */
    override val itemsRepository: ItemsRepository by lazy {
        // Membuat instance `OfflineItemsRepository` menggunakan `ItemDao` dari `InventoryDatabase`.
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}

/*
Penjelasan:
1. `AppContainer`
   - Interface ini mendefinisikan `itemsRepository` sebagai properti yang harus diimplementasikan oleh kelas yang menggunakannya.
   - Memudahkan pengelolaan dependensi, sehingga komponen aplikasi yang memerlukan `ItemsRepository` dapat dengan mudah mendapatkannya.

2. `AppDataContainer`
   - Kelas ini mengimplementasikan `AppContainer` dan bertanggung jawab menyediakan instance `ItemsRepository`.
   - Konstruktor menerima `context` yang diperlukan untuk menginisialisasi `InventoryDatabase`.
   - `itemsRepository` diinisialisasi menggunakan `by lazy`, sehingga dependensi hanya dibuat saat diperlukan.

Dengan pendekatan ini, Anda mendapatkan struktur yang bersih untuk Dependency Injection,
mempermudah pengelolaan dependensi di aplikasi, meningkatkan efisiensi, dan membuat kode lebih mudah diuji.
*/