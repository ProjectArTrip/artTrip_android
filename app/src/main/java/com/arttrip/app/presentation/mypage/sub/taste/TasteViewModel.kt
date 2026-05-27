package com.arttrip.app.presentation.mypage.sub.taste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.ui.UiMessage
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.profile.ObserveProfileUseCase
import com.arttrip.app.domain.usecase.userTaste.GetAllTasteGroupsUseCase
import com.arttrip.app.domain.usecase.userTaste.GetUserTasteGroupsUseCase
import com.arttrip.app.domain.usecase.userTaste.SaveUserTasteUseCase
import com.arttrip.app.presentation.mypage.sub.taste.contract.TasteEffect
import com.arttrip.app.presentation.mypage.sub.taste.contract.TasteIntent
import com.arttrip.app.presentation.mypage.sub.taste.contract.TasteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasteViewModel
    @Inject
    constructor(
        private val getAllTasteGroupsUseCase: GetAllTasteGroupsUseCase,
        private val observeProfile: ObserveProfileUseCase,
        private val getUserTasteGroupsUseCase: GetUserTasteGroupsUseCase,
        private val saveUserTasteUseCase: SaveUserTasteUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(TasteState())
        val state: StateFlow<TasteState> = _state

        private val _effect = MutableSharedFlow<TasteEffect>()
        val effect: SharedFlow<TasteEffect> = _effect

        init {
            viewModelScope.launch {
                observeProfile().collect { profile ->
                    if (profile != null) {
                        _state.update {
                            it.copy(
                                nickname = profile.nickname,
                            )
                        }
                    }
                }
            }
        }

        fun onIntent(intent: TasteIntent) {
            when (intent) {
                is TasteIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(TasteEffect.NavigateBack) }
                }
                is TasteIntent.Initialize -> {
                    loadTasteOptions()
                }
                is TasteIntent.ToggleGenre -> handleToggleGenre(intent.name)
                is TasteIntent.ToggleStyle -> handleToggleStyle(intent.name)
                is TasteIntent.SaveClicked -> {
                    handleClickSave()
                }
            }
        }

        private fun loadTasteOptions() {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true, errorMessage = null) }

                val ok = loadAllTasteGroups()
                if (!ok) return@launch

                loadUserTasteSelection()
            }
        }

        private suspend fun loadAllTasteGroups(): Boolean =
            when (val all = getAllTasteGroupsUseCase().first { it !is ApiResult.Loading }) {
                is ApiResult.Success -> {
                    val groups = all.data
                    _state.update { state ->
                        state.copy(
                            genres = groups.genres,
                            styles = groups.styles,
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                    true
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = "키워드를 가져오는데 실패했습니다.") }
                    false
                }
                is ApiResult.Loading -> true
            }

        private suspend fun loadUserTasteSelection() {
            when (val user = getUserTasteGroupsUseCase().first { it !is ApiResult.Loading }) {
                is ApiResult.Success -> {
                    val groups = user.data
                    _state.update { state ->
                        state.copy(
                            selectedGenresNames = groups.genres.map { taste -> taste.name }.toSet(),
                            selectedStyleNames = groups.styles.map { taste -> taste.name }.toSet(),
                        )
                    }
                }
                is ApiResult.Error -> Unit
                is ApiResult.Loading -> Unit
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
                            _effect.emit(TasteEffect.ShowToastAndNavigateBack(UiMessage.TASTE_SAVED))
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
