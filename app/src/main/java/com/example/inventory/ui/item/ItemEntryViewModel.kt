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

class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    // Menyimpan status UI item yang sedang dimasukkan
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /*
     * Memperbarui status UI dengan detail item yang baru dan memvalidasi input.
     * Fungsi ini dipanggil saat pengguna mengubah detail item (nama, harga, kuantitas).
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /*
     * Menyimpan item ke repositori jika input valid.
     * Fungsi ini dijalankan secara asynchronous (suspend).
     */
    suspend fun saveItem() {
        if (validateInput()) {
            // Memasukkan item ke repositori jika valid
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    /*
     * Memvalidasi input apakah nama, harga, dan kuantitas tidak kosong.
     * Fungsi ini digunakan untuk memeriksa apakah input sudah valid.
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

/*
 * Data class untuk menyimpan status UI terkait item.
 * Menyimpan detail item dan status validitas entri item.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

/*
 * Data class untuk menyimpan detail item yang akan dimasukkan.
 * Berisi id, nama, harga, dan kuantitas.
 */
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

// Fungsi untuk mengonversi ItemDetails menjadi objek Item yang akan disimpan ke repositori.
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0, // Mengonversi harga ke Double, default 0.0 jika gagal
    quantity = quantity.toIntOrNull() ?: 0  // Mengonversi kuantitas ke Int, default 0 jika gagal
)

// Fungsi untuk memformat harga item dalam format mata uang.
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

// Fungsi untuk mengonversi objek Item menjadi ItemDetails yang akan digunakan untuk memperbarui UI.
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)