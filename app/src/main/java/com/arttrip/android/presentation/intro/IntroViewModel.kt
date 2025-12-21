package com.arttrip.android.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.userkeyword.GetAllKeywordsUseCase
import com.arttrip.android.domain.usecase.userkeyword.SaveUserKeywordsUseCase
import com.arttrip.android.presentation.intro.contract.IntroEffect
import com.arttrip.android.presentation.intro.contract.IntroIntent
import com.arttrip.android.presentation.intro.contract.IntroState
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
        private val getAllKeywordsUseCase: GetAllKeywordsUseCase,
        private val saveUserKeywordsUseCase: SaveUserKeywordsUseCase,
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
                is IntroIntent.ToggleGenre -> handleToggleGenre(intent.id)
                is IntroIntent.ToggleStyle -> handleToggleStyle(intent.id)
                is IntroIntent.ClickNext -> handleClickNext()
            }
        }

        private fun loadIntroOptions() {
            viewModelScope.launch {
                getAllKeywordsUseCase().collect { result ->
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

        private fun handleToggleGenre(id: Int) {
            _state.update { state ->
                val newGenres = toggleId(state.selectedGenreIds, id)
                state.copy(
                    selectedGenreIds = newGenres,
                )
            }
        }

        private fun handleToggleStyle(id: Int) {
            _state.update { state ->
                val newStyles = toggleId(state.selectedStyleIds, id)
                state.copy(
                    selectedStyleIds = newStyles,
                )
            }
        }

        private fun handleClickNext() {
            val current = _state.value
            if (!current.isNextEnabled || current.isLoading) return

            viewModelScope.launch {
                saveUserKeywordsUseCase(
                    genreIds = current.selectedGenreIds,
                    styleIds = current.selectedStyleIds,
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
                        }
                    }
                }
            }
        }

        private fun toggleId(
            set: Set<Int>,
            id: Int,
        ): Set<Int> = if (id in set) set - id else set + id
    }
