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

package com.example.inventory.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.item.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/*
 * HomeScreen adalah fungsi Composable yang berfungsi sebagai layar utama aplikasi.
 * Layar ini menampilkan daftar item dan menyediakan tombol untuk menambah item baru.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit, // Fungsi untuk navigasi ke layar input item baru
    navigateToItemUpdate: (Int) -> Unit, // Fungsi untuk navigasi ke layar update item berdasarkan id
    modifier: Modifier = Modifier // Modifier untuk kustomisasi tampilan
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Scaffold untuk membuat struktur halaman dengan TopAppBar dan FloatingActionButton
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            // FloatingActionButton untuk menambah item baru
            FloatingActionButton(
                onClick = navigateToItemEntry, // Mengarahkan ke halaman input item
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        // Menampilkan konten HomeBody yang berisi daftar item
        HomeBody(
            itemList = listOf(), // Daftar item
            onItemClick = navigateToItemUpdate, // Fungsi untuk mengupdate item
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

// HomeBody adalah fungsi yang menampilkan daftar item atau pesan jika tidak ada item.
@Composable
private fun HomeBody(
    itemList: List<Item>, // Daftar item yang akan ditampilkan
    onItemClick: (Int) -> Unit, // Fungsi yang akan dipanggil saat item diklik
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (itemList.isEmpty()) {
            // Menampilkan teks jika daftar item kosong
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            // Menampilkan daftar item menggunakan InventoryList
            InventoryList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) }, // Mengirim id item untuk update
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

// InventoryList adalah fungsi untuk menampilkan daftar item dalam bentuk LazyColumn.
@Composable
private fun InventoryList(
    itemList: List<Item>, // Daftar item yang akan ditampilkan
    onItemClick: (Item) -> Unit, // Fungsi yang dipanggil saat item diklik
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { item ->
            // Menampilkan item di dalam InventoryItem yang bisa diklik
            InventoryItem(item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

// InventoryItem menampilkan informasi item dalam bentuk card, termasuk nama, harga, dan stok.
@Composable
private fun InventoryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Menampilkan nama item dan harga
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f)) // Memberi spasi antara nama dan harga
                Text(
                    text = item.formatedPrice(), // Format harga item
                    style = MaterialTheme.typography.titleMedium
                )
            }
            // Menampilkan jumlah stok item
            Text(
                text = stringResource(R.string.in_stock, item.quantity),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// Fungsi preview untuk menampilkan contoh tampilan HomeBody dengan item.
@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        HomeBody(listOf(
            Item(1, "Game", 100.0, 20), Item(2, "Pen", 200.0, 30), Item(3, "TV", 300.0, 50)
        ), onItemClick = {})
    }
}

// Fungsi preview untuk menampilkan tampilan HomeBody saat tidak ada item.
@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    InventoryTheme {
        HomeBody(listOf(), onItemClick = {})
    }
}

// Fungsi preview untuk menampilkan tampilan InventoryItem.
@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        InventoryItem(
            Item(1, "Game", 100.0, 20),
        )
    }
}