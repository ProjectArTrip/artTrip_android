package com.arttrip.android.presentation.my.sub.taste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.userTaste.GetTasteGroupsUseCase
import com.arttrip.android.domain.usecase.userTaste.SaveUserTasteUseCase
import com.arttrip.android.presentation.my.sub.taste.contract.TasteEffect
import com.arttrip.android.presentation.my.sub.taste.contract.TasteIntent
import com.arttrip.android.presentation.my.sub.taste.contract.TasteState
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
        private val getTasteGroupsUseCase: GetTasteGroupsUseCase,
        private val saveUserTasteUseCase: SaveUserTasteUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(TasteState())
        val state: StateFlow<TasteState> = _state

        private val _effect = MutableSharedFlow<TasteEffect>()
        val effect: SharedFlow<TasteEffect> = _effect

        fun onIntent(intent: TasteIntent) {
            when (intent) {
                is TasteIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(TasteEffect.NavigateBack) }
                }
                is TasteIntent.Initialize -> {
                    loadIntroOptions()
                }
                is TasteIntent.ToggleGenre -> handleToggleGenre(intent.name)
                is TasteIntent.ToggleStyle -> handleToggleStyle(intent.name)
                is TasteIntent.SaveClicked -> {
                    // handleClickSave()
                    viewModelScope.launch { _effect.emit(TasteEffect.ShowToastAndNavigateBack("장르 및 스타일이 저장되었습니다.")) }
                }
            }
        }

        private fun loadIntroOptions() {
            viewModelScope.launch {
                getTasteGroupsUseCase().collect { result ->
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
                val newGenres = toggleTaste(state.selectedGenresNames, name)
                state.copy(
                    selectedGenresNames = newGenres,
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

        private fun handleClickSave() {
            val current = _state.value
            if (!current.isNextEnabled || current.isLoading) return

            viewModelScope.launch {
                saveUserTasteUseCase(
                    genres = current.selectedGenresNames,
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
                            _effect.emit(TasteEffect.NavigateBack)
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "키워드 설정에 실패하였습니다.",
                                )
                            }
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
