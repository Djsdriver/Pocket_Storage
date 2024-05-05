package com.example.pocketstorage.di.excel

import android.content.Context
import com.example.pocketstorage.data.excel.ExcelDataSource
import com.example.pocketstorage.data.excel.ExcelDataSourceApachePOI
import com.example.pocketstorage.data.repository.ExcelRepositoryImpl
import com.example.pocketstorage.domain.repository.ExcelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    
    @Provides
    @Singleton
    fun provideExcelDataSource(@ApplicationContext context: Context)
    : ExcelDataSource {
        return ExcelDataSourceApachePOI(context = context)
    }

    @Provides
    @Singleton
    fun provideExcelRepository(excelDataSource: ExcelDataSource): ExcelRepository {
        return ExcelRepositoryImpl(excelDataSource = excelDataSource)
    }
}