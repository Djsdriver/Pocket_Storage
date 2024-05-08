package com.example.pocketstorage.domain.usecase.excel

import com.example.pocketstorage.domain.model.TableInventory
import com.example.pocketstorage.domain.repository.ExcelRepository

class ExportInventoriesToExcelFileUseCase(private val excelRepository: ExcelRepository) {
    operator fun invoke(tableInventoryList: List<TableInventory>) {
        excelRepository.exportTableInventoryListToExcelFile(tableInventoryList)
    }
}