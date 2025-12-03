package com.arttrip.android.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.usecase.intro.SaveIntroKeywordsUseCase
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
        private val saveIntroKeywordsUseCase: SaveIntroKeywordsUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(IntroState())
        val state: StateFlow<IntroState> = _state

        private val _effect = MutableSharedFlow<IntroEffect>()
        val effect: SharedFlow<IntroEffect> = _effect

        fun onIntent(intent: IntroIntent) {
            when (intent) {
                is IntroIntent.ToggleGenre -> handleToggleGenre(intent.id)
                is IntroIntent.ToggleStyle -> handleToggleStyle(intent.id)
                IntroIntent.ClickNext -> handleClickNext()
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
                _state.update { it.copy(isLoading = true, errorMessage = null) }
                runCatching {
                    saveIntroKeywordsUseCase(
                        genreIds = current.selectedGenreIds,
                        styleIds = current.selectedStyleIds,
                    )
                }.onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _effect.emit(IntroEffect.NavigateToHome)
                }.onFailure { e ->
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                    _effect.emit(IntroEffect.ShowError(e.message ?: "알 수 없는 오류가 발생했어요."))
                }
            }
        }

        private fun toggleId(
            list: List<Int>,
            id: Int,
        ): List<Int> = if (id in list) list - id else list + id
    }
