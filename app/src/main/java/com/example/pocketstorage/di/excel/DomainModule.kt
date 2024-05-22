package com.example.pocketstorage.di.excel

import com.example.pocketstorage.domain.repository.ExcelRepository
import com.example.pocketstorage.domain.usecase.excel.ExportInventoriesToExcelFileUseCase
import com.example.pocketstorage.domain.usecase.excel.ImportInventoriesFromExcelFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideExportInventoriesToExcelFileUseCase(excelRepository: ExcelRepository)
            : ExportInventoriesToExcelFileUseCase {
        return ExportInventoriesToExcelFileUseCase(excelRepository)
    }

    @Provides
    fun provideImportInventoriesFromExcelFileUseCase(excelRepository: ExcelRepository)
            : ImportInventoriesFromExcelFileUseCase {
        return ImportInventoriesFromExcelFileUseCase(excelRepository)
    }
}
