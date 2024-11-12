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

object AppViewModelProvider {
    /*
     * Factory untuk membuat instance dari berbagai ViewModel.
     * ViewModel ini digunakan untuk mengelola status UI dan logika bisnis dalam aplikasi.
     */
    val Factory = viewModelFactory {

        /*
         * Inisialisasi untuk ItemEditViewModel.
         * viewModelFactory memastikan bahwa setiap ViewModel memiliki dependensi yang diperlukan.
         * `SavedStateHandle` digunakan untuk menyimpan dan memulihkan status UI.
         */
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()   // Mengirimkan SavedStateHandle ke ViewModel
            )
        }

        /*
         * Inisialisasi untuk ItemEntryViewModel.
         * `inventoryApplication().container.itemsRepository` digunakan untuk mendapatkan repository.
         */
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository) // Mengirimkan repository yang diperlukan
        }

        /*
         * Inisialisasi untuk ItemDetailsViewModel.
         * ViewModel ini memerlukan SavedStateHandle untuk menyimpan status entri item.
         */
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle() // Mengirimkan SavedStateHandle ke ViewModel
            )
        }

        /*
         * Inisialisasi untuk HomeViewModel.
         * HomeViewModel dibuat tanpa dependensi tambahan, karena tidak memerlukan parameter.
         */
        initializer {
            HomeViewModel() // Membuat instance HomeViewModel tanpa dependensi tambahan
        }
    }
}

/*
 * Ekstensi fungsi untuk mendapatkan aplikasi InventoryApplication dari CreationExtras.
 * Digunakan untuk mengakses container aplikasi yang menyimpan dependensi seperti repository.
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)

