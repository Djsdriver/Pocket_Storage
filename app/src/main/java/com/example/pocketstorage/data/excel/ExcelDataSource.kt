package com.example.pocketstorage.data.excel

import com.example.pocketstorage.domain.model.TableInventory

interface ExcelDataSource {
    fun exportInventoryToExcelFile(tableInventoryList: List<TableInventory>)

    suspend fun importInventoryFromExcelFile(uriFile :String?) : List<TableInventory>
}
