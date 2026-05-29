package com.example.magnoliaapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.magnoliaapp.MagnoliasApplication
import com.example.magnoliaapp.data.MagnoliasRepository
import com.example.magnoliaapp.model.Magnolia
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Magnolia screens
 */
sealed interface MagnoliaUiState {

    /**
     * Success state for magnolias list
     */
    data class Success(
        val magnolias: List<Magnolia>
    ) : MagnoliaUiState

    /**
     * Success state for a single magnolia
     */
    data class DetailsSuccess(
        val magnolia: Magnolia
    ) : MagnoliaUiState

    /**
     * Error state
     */
    object Error : MagnoliaUiState

    /**
     * Loading state
     */
    object Loading : MagnoliaUiState
}

class MagnoliaViewModel(
    private val magnoliasRepository: MagnoliasRepository
) : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var magnoliaUiState: MagnoliaUiState by mutableStateOf(
        MagnoliaUiState.Loading
    )
        private set

    /**
     * Call getMagnolias() on init
     * so we can display status immediately.
     */
    init {
        getMagnolias()
    }

    /**
     * Gets all magnolias from the Magnolia API Retrofit service
     * and updates the UI state.
     */
    fun getMagnolias() {

        viewModelScope.launch {

            magnoliaUiState = MagnoliaUiState.Loading

            magnoliaUiState = try {

                val listResult =
                    magnoliasRepository.getMagnolias()

                MagnoliaUiState.Success(
                    magnolias = listResult
                )

            } catch (e: IOException) {

                MagnoliaUiState.Error

            } catch (e: HttpException) {

                MagnoliaUiState.Error
            }
        }
    }

    /**
     * Gets a single Magnolia from the Magnolia API Retrofit service
     * and updates the UI state.
     */
    fun getMagnolia(id: Int) {

        viewModelScope.launch {

            magnoliaUiState = MagnoliaUiState.Loading

            magnoliaUiState = try {

                val magnolia =
                    magnoliasRepository.getMagnolia(id)

                MagnoliaUiState.DetailsSuccess(
                    magnolia = magnolia
                )

            } catch (e: IOException) {

                MagnoliaUiState.Error

            } catch (e: HttpException) {

                MagnoliaUiState.Error
            }
        }
    }

    /**
     * Factory for [MagnoliaViewModel]
     * that takes [MagnoliasRepository]
     * as a dependency.
     */
    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {

                initializer {

                    /*
                     * Use a safe cast for the application context to avoid NullPointerException
                     * during Compose Previews.
                     */
                    val application =
                        (this[APPLICATION_KEY]
                                as? MagnoliasApplication)

                    /*
                     * Provide a dummy repository if the application context is not available
                     * (e.g., in a Preview environment).
                     */
                    val magnoliasRepository =
                        application?.container?.magnoliasRepository
                            ?: object : MagnoliasRepository {
                                override suspend fun getMagnolias(): List<Magnolia> =
                                    emptyList()

                                override suspend fun getMagnolia(id: Int): Magnolia =
                                    Magnolia(0, "", 0.0, 0.0, "")
                            }

                    MagnoliaViewModel(
                        magnoliasRepository =
                            magnoliasRepository
                    )
                }
            }
    }
}
