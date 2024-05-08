package com.example.pocketstorage.domain.repository

import com.example.pocketstorage.domain.model.TableInventory

interface ExcelRepository {

    fun exportTableInventoryListToExcelFile(tableInventoryList: List<TableInventory>)
}