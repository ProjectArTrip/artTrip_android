package com.arttrip.android.presentation.exhibition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.exhibition.GetExhibitionDetailUseCase
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExhibitionDetailViewModel
    @Inject
    constructor(
        private val getExhibitionDetailUseCase: GetExhibitionDetailUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ExhibitionDetailState())
        val state: StateFlow<ExhibitionDetailState> = _state

        private val _effect = MutableSharedFlow<ExhibitionDetailEffect>()
        val effect: SharedFlow<ExhibitionDetailEffect> = _effect

        fun onIntent(intent: ExhibitionDetailIntent) {
            when (intent) {
                is ExhibitionDetailIntent.Initialize -> {
                    loadExhibitionDetail(intent.exhibitId)
                }
                is ExhibitionDetailIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ExhibitionDetailEffect.NavigateBack)
                    }
                }
            }
        }

        private fun loadExhibitionDetail(exhibitId: Int) {
            viewModelScope.launch {
                getExhibitionDetailUseCase(exhibitId).collect { result ->
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
                            _state.update {
                                it.copy(
                                    detail = result.data,
                                    isLoading = false,
                                    errorMessage = null,
                                )
                            }
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "전시 상세를 가져오는데 실패했습니다.",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
