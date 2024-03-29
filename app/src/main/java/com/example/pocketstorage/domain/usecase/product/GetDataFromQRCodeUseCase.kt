package com.example.pocketstorage.domain.usecase.product

import com.example.pocketstorage.domain.repository.ScannerRepository

class GetDataFromQRCodeUseCase(private val scannerRepository: ScannerRepository) {

    operator fun invoke() = scannerRepository.scanProduct()

}