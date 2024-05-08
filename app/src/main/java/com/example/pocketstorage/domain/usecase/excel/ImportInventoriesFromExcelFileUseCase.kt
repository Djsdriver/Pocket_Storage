package com.example.pocketstorage.domain.usecase.excel

import com.example.pocketstorage.domain.model.TableInventory
import com.example.pocketstorage.domain.repository.ExcelRepository

class ImportInventoriesFromExcelFileUseCase(private val excelRepository: ExcelRepository) {
    suspend operator fun invoke(uriFile: String?) : List<TableInventory> {
        return excelRepository.importTableInventoryListFromExcelFile(uriFile)
    }
}
