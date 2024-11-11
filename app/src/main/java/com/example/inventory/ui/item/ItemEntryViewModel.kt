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

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/**
 * `ItemEntryViewModel` adalah ViewModel yang bertanggung jawab untuk memvalidasi dan memasukkan item
 * ke dalam database Room. ViewModel ini mengelola status UI item dan memastikan bahwa data yang dimasukkan
 * valid sebelum disimpan ke database.
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * Menyimpan status UI saat ini untuk item.
     * - `itemUiState`: Variabel ini digunakan untuk memegang data saat ini terkait item.
     * - `by mutableStateOf`: Menggunakan properti mutable state dari Jetpack Compose untuk membuat UI reaktif.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Memperbarui `itemUiState` dengan nilai yang diberikan dalam argumen. Metode ini juga memicu
     * validasi untuk memastikan nilai input benar.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /**
     * Menyimpan item ke dalam database jika inputnya valid.
     * - `suspend`: Metode ini dijalankan secara asinkron menggunakan coroutine.
     */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    /**
     * Memvalidasi input dari `ItemDetails`. Mengembalikan `true` jika semua input tidak kosong.
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

/**
 * `ItemUiState` merepresentasikan status UI untuk sebuah item.
 * - `itemDetails`: Menyimpan rincian item.
 * - `isEntryValid`: Menyatakan apakah input yang diberikan valid.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

/**
 * `ItemDetails` menyimpan rincian dari item, dengan semua properti dalam bentuk string
 * untuk mempermudah validasi input.
 */
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/**
 * Fungsi ekstensi untuk mengonversi `ItemDetails` ke `Item`.
 * - Jika `price` tidak valid sebagai `Double`, harga diatur ke 0.0.
 * - Jika `quantity` tidak valid sebagai `Int`, jumlah diatur ke 0.
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/**
 * Fungsi ekstensi untuk memformat harga dari `Item` ke format mata uang.
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Fungsi ekstensi untuk mengonversi `Item` ke `ItemUiState`.
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/**
 * Fungsi ekstensi untuk mengonversi `Item` ke `ItemDetails`.
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
