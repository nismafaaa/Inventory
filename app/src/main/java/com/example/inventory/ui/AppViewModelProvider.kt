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

package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

/**
 * Menyediakan Factory untuk membuat instance ViewModel untuk seluruh aplikasi Inventory.
 *
 * Objek ini mendefinisikan `Factory` menggunakan `viewModelFactory` yang berisi inisialisasi
 * untuk setiap ViewModel yang digunakan di aplikasi. Setiap `initializer` mendefinisikan
 * cara membuat instance dari masing-masing ViewModel, yang memungkinkan penyediaan dependensi
 * yang diperlukan seperti `itemsRepository` atau `SavedStateHandle`.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inisialisasi untuk ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()   // Mengirimkan SavedStateHandle ke ViewModel
            )
        }
        // Inisialisasi untuk ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)     // Mengirimkan repository yang diperlukan
        }

        // Inisialisasi untuk ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()   // Mengirimkan SavedStateHandle ke ViewModel
            )
        }

        // Inisialisasi untuk HomeViewModel
        initializer {
            HomeViewModel() // Membuat instance HomeViewModel tanpa dependensi tambahan
        }
    }
}

/**
 * Fungsi ekstensi untuk mendapatkan objek 'Application' dan mengembalikan instance
 * dari 'InventoryApplication'.
 *
 * Fungsi ini mengakses `APPLICATION_KEY` untuk mengambil instance `Application` yang ada,
 * kemudian mengonversinya menjadi `InventoryApplication`. Ini berguna saat Anda membutuhkan
 * akses ke konteks aplikasi atau dependensi global dalam `ViewModel`.
 *
 * @return Instance dari `InventoryApplication` yang saat ini aktif.
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
