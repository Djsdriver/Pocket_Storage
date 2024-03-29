package com.example.pocketstorage.di.scanner

import android.app.Application
import android.content.Context
import com.example.pocketstorage.data.repository.PreferencesRepositoryImpl
import com.example.pocketstorage.data.repository.ScannerRepositoryImpl
import com.example.pocketstorage.domain.repository.PreferencesRepository
import com.example.pocketstorage.domain.repository.ScannerRepository
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindScannerRepository(
        scannerRepository : ScannerRepositoryImpl
    ) : ScannerRepository



}
