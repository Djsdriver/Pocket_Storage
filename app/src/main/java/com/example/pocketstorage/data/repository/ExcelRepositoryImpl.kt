package com.example.pocketstorage.data.repository

import com.example.pocketstorage.data.excel.ExcelDataSource
import com.example.pocketstorage.domain.model.TableInventory
import com.example.pocketstorage.domain.repository.ExcelRepository

class ExcelRepositoryImpl(private val excelDataSource: ExcelDataSource) : ExcelRepository {
    override fun exportTableInventoryListToExcelFile(tableInventoryList: List<TableInventory>) {
        excelDataSource.exportInventoryToExcelFile(tableInventoryList)
    }

    override suspend fun importTableInventoryListFromExcelFile(uriFile: String?): List<TableInventory> {
        return excelDataSource.importInventoryFromExcelFile(uriFile)
    }
}
