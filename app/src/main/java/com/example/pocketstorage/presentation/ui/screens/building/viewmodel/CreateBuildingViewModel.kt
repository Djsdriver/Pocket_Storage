package com.example.pocketstorage.presentation.ui.screens.building.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.usecase.db.InsertLocationUseCase
import com.example.pocketstorage.presentation.ui.screens.building.CreateBuildingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBuildingViewModel @Inject constructor(
    private val insertLocationUseCase: InsertLocationUseCase
) :ViewModel(){


    private val _state = MutableStateFlow(Location())
    val state = _state.asStateFlow()

    fun event(createBuildingEvent: CreateBuildingEvent){
        when(createBuildingEvent){
            is CreateBuildingEvent.CreateBuilding-> {
                val name = state.value.name
                val index = state.value.index
                val address = state.value.address

                if (name.isBlank() || index.isBlank() || address.isBlank()) {
                    return
                }

                val building = Location(
                    name = name,
                    index = index,
                    address = address
                )
                _state.update {
                    it.copy(
                        isSaved = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    insertLocationUseCase(location = building)

                    _state.update {
                        it.copy(
                            name = "",
                            index = "",
                            address = "",
                        )
                    }
                }



            }

            is CreateBuildingEvent.SetNameBuilding-> {
                _state.update {
                    it.copy(
                        name = createBuildingEvent.nameBuilding
                    )
                }
            }
            is CreateBuildingEvent.SetIndex ->{
                _state.update {
                    it.copy(
                        index = createBuildingEvent.index
                    )
                }
            }
            is CreateBuildingEvent.SetAddress->{
                _state.update {
                    it.copy(
                        address = createBuildingEvent.address
                    )
                }
            }


            else -> {}
        }
    }

}
