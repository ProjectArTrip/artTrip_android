package com.arttrip.app.presentation.intro.taste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.ui.UiMessage
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.userTaste.GetAllTasteGroupsUseCase
import com.arttrip.app.domain.usecase.userTaste.SaveUserTasteUseCase
import com.arttrip.app.presentation.intro.taste.contract.TasteEffect
import com.arttrip.app.presentation.intro.taste.contract.TasteIntent
import com.arttrip.app.presentation.intro.taste.contract.TasteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasteViewModel
    @Inject
    constructor(
        private val getAllTasteGroupsUseCase: GetAllTasteGroupsUseCase,
        private val saveUserTasteUseCase: SaveUserTasteUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(TasteState())
        val state: StateFlow<TasteState> = _state

        private val _effect = MutableSharedFlow<TasteEffect>()
        val effect: SharedFlow<TasteEffect> = _effect

        fun onIntent(intent: TasteIntent) {
            when (intent) {
                is TasteIntent.Initialize -> {
                    loadTasteOptions()
                }
                is TasteIntent.ToggleGenre -> handleToggleGenre(intent.name)
                is TasteIntent.ToggleStyle -> handleToggleStyle(intent.name)
                is TasteIntent.ClickNext -> handleClickNext()
            }
        }

        private fun loadTasteOptions() {
            viewModelScope.launch {
                getAllTasteGroupsUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            val groups = result.data

                            _state.update {
                                it.copy(
                                    genres = groups.genres,
                                    styles = groups.styles,
                                    isLoading = false,
                                    errorMessage = null,
                                )
                            }
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "키워드를 가져오는데 실패했습니다.",
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun handleToggleGenre(name: String) {
            _state.update { state ->
                val newGenres = toggleTaste(state.selectedGenreNames, name)
                state.copy(
                    selectedGenreNames = newGenres,
                )
            }
        }

        private fun handleToggleStyle(name: String) {
            _state.update { state ->
                val newStyles = toggleTaste(state.selectedStyleNames, name)
                state.copy(
                    selectedStyleNames = newStyles,
                )
            }
        }

        private fun handleClickNext() {
            val current = _state.value
            if (!current.isNextEnabled || current.isLoading) return

            viewModelScope.launch {
                saveUserTasteUseCase(
                    genres = current.selectedGenreNames,
                    styles = current.selectedStyleNames,
                ).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(TasteEffect.NavigateToHome)
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "키워드 설정에 실패하였습니다.",
                                )
                            }
                            _effect.emit(TasteEffect.ShowError(UiMessage.ERROR_TEMP_SAVE_RETRY))
                        }
                    }
                }
            }
        }

        private fun toggleTaste(
            set: Set<String>,
            name: String,
        ): Set<String> = if (name in set) set - name else set + name
    }
