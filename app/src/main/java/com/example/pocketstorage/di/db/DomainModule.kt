package com.example.pocketstorage.di.db

import com.example.pocketstorage.domain.repository.DatabaseRepository
import com.example.pocketstorage.domain.usecase.db.DeleteCategoryUseCase
import com.example.pocketstorage.domain.usecase.db.DeleteInventoryUseCase
import com.example.pocketstorage.domain.usecase.db.DeleteLocationUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoriesUseCase
import com.example.pocketstorage.domain.usecase.db.GetCategoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByCategoryIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesByLocationIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoriesUseCase
import com.example.pocketstorage.domain.usecase.db.GetInventoryByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationByIdUseCase
import com.example.pocketstorage.domain.usecase.db.GetLocationsUseCase
import com.example.pocketstorage.domain.usecase.db.InsertCategoryUseCase
import com.example.pocketstorage.domain.usecase.db.InsertInventoryUseCase
import com.example.pocketstorage.domain.usecase.db.InsertLocationUseCase
import com.example.pocketstorage.domain.usecase.db.UpdateInventoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideDeleteCategoryUseCase(databaseRepository: DatabaseRepository)
    : DeleteCategoryUseCase {
        return DeleteCategoryUseCase(databaseRepository)
    }

    @Provides
    fun provideDeleteInventoryUseCase(databaseRepository: DatabaseRepository)
    : DeleteInventoryUseCase {
        return DeleteInventoryUseCase(databaseRepository)
    }

    @Provides
    fun provideDeleteLocationUseCase(databaseRepository: DatabaseRepository)
    : DeleteLocationUseCase {
        return DeleteLocationUseCase(databaseRepository)
    }

    @Provides
    fun provideGetCategoriesUseCase(databaseRepository: DatabaseRepository)
    : GetCategoriesUseCase {
        return GetCategoriesUseCase(databaseRepository)
    }

    @Provides
    fun provideGetCategoryByIdUseCase(databaseRepository: DatabaseRepository)
    : GetCategoryByIdUseCase {
        return GetCategoryByIdUseCase(databaseRepository)
    }

    @Provides
    fun provideGetInventoriesByCategoryIdUseCase(databaseRepository: DatabaseRepository)
    : GetInventoriesByCategoryIdUseCase {
        return GetInventoriesByCategoryIdUseCase(databaseRepository)
    }

    @Provides
    fun providesGetInventoriesByLocationIdUseCase(databaseRepository: DatabaseRepository)
    : GetInventoriesByLocationIdUseCase {
        return GetInventoriesByLocationIdUseCase(databaseRepository)
    }

    @Provides
    fun provideGetInventoriesUseCase(databaseRepository: DatabaseRepository)
    : GetInventoriesUseCase {
        return GetInventoriesUseCase(databaseRepository)
    }

    @Provides
    fun provideGetInventoryByIdUseCase(databaseRepository: DatabaseRepository)
    : GetInventoryByIdUseCase {
        return GetInventoryByIdUseCase(databaseRepository)
    }

    @Provides
    fun provideGetLocationByIdUseCase(databaseRepository: DatabaseRepository)
    : GetLocationByIdUseCase {
        return GetLocationByIdUseCase(databaseRepository)
    }

    @Provides
    fun provideGetLocationsUseCase(databaseRepository: DatabaseRepository)
    : GetLocationsUseCase {
        return GetLocationsUseCase(databaseRepository)
    }

    @Provides
    fun provideInsertCategoryUseCase(databaseRepository: DatabaseRepository)
    : InsertCategoryUseCase {
        return InsertCategoryUseCase(databaseRepository)
    }

    @Provides
    fun provideInsertInventoryUseCase(databaseRepository: DatabaseRepository)
    : InsertInventoryUseCase {
        return InsertInventoryUseCase(databaseRepository)
    }

    @Provides
    fun provideInsertLocationUseCase(databaseRepository: DatabaseRepository)
    : InsertLocationUseCase {
        return InsertLocationUseCase(databaseRepository)
    }

    @Provides
    fun provideUpdateInventoryUseCase(databaseRepository: DatabaseRepository)
    : UpdateInventoryUseCase {
        return UpdateInventoryUseCase(databaseRepository)
    }
}