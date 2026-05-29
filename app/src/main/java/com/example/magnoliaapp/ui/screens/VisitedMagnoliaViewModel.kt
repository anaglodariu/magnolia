package com.example.magnoliaapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.magnoliaapp.MagnoliasApplication
import com.example.magnoliaapp.data.VisitedMagnolia
import com.example.magnoliaapp.data.VisitedMagnoliasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class VisitedMagnoliaViewModel(

    private val repository: VisitedMagnoliasRepository

) : ViewModel() {

    val visitedMagnoliaIds: StateFlow<Set<Int>> =

        repository
            .getVisitedMagnolias()
            .map { list ->

                list.map {
                    it.magnoliaId
                }.toSet()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptySet()
            )

    fun markVisited(
        magnoliaId: Int
    ) {

        viewModelScope.launch {

            repository.insertVisited(
                magnoliaId
            )
        }
    }

    fun unmarkVisited(
        magnoliaId: Int
    ) {

        viewModelScope.launch {

            repository.deleteVisited(
                magnoliaId
            )
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {

                initializer {

                    val application =
                        (this[APPLICATION_KEY]
                                as? MagnoliasApplication)

                    val repository =
                        application?.container?.visitedMagnoliasRepository
                            ?: object : VisitedMagnoliasRepository {

                                override fun getVisitedMagnolias() =
                                    flowOf<List<VisitedMagnolia>>(emptyList())

                                override fun isVisited(id: Int) =
                                    flowOf(false)

                                override suspend fun insertVisited(
                                    magnoliaId: Int
                                ) {
                                }

                                override suspend fun deleteVisited(
                                    magnoliaId: Int
                                ) {
                                }
                            }

                    VisitedMagnoliaViewModel(
                        repository = repository
                    )
                }
            }
    }
}