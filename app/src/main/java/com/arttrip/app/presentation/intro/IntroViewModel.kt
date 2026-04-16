package com.arttrip.app.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.ui.UiMessage
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.userTaste.GetAllTasteGroupsUseCase
import com.arttrip.app.domain.usecase.userTaste.SaveUserTasteUseCase
import com.arttrip.app.presentation.intro.contract.IntroEffect
import com.arttrip.app.presentation.intro.contract.IntroIntent
import com.arttrip.app.presentation.intro.contract.IntroState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel
    @Inject
    constructor(
        private val getAllTasteGroupsUseCase: GetAllTasteGroupsUseCase,
        private val saveUserTasteUseCase: SaveUserTasteUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(IntroState())
        val state: StateFlow<IntroState> = _state

        private val _effect = MutableSharedFlow<IntroEffect>()
        val effect: SharedFlow<IntroEffect> = _effect

        fun onIntent(intent: IntroIntent) {
            when (intent) {
                is IntroIntent.Initialize -> {
                    loadIntroOptions()
                }
                is IntroIntent.ToggleGenre -> handleToggleGenre(intent.name)
                is IntroIntent.ToggleStyle -> handleToggleStyle(intent.name)
                is IntroIntent.ClickNext -> handleClickNext()
            }
        }

        private fun loadIntroOptions() {
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
                            _effect.emit(IntroEffect.NavigateToHome)
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "키워드 설정에 실패하였습니다.",
                                )
                            }
                            _effect.emit(IntroEffect.ShowError(UiMessage.ERROR_TEMP_SAVE_RETRY))
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
