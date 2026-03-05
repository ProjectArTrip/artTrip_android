package com.arttrip.android.presentation.my.sub.recentexhibitions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.exhibition.GetUserRecentExhibitionsUseCase
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsEffect
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsIntent
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val RECENT_THUMB_QUERY =
    ImageQueryParams(
        widthPx = 100,
        heightPx = 100,
    )

@HiltViewModel
class RecentExhibitionsViewModel
    @Inject
    constructor(
        private val getUserRecentExhibitionsUseCase: GetUserRecentExhibitionsUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(RecentExhibitionsState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<RecentExhibitionsEffect>()
        val effect = _effect.asSharedFlow()

        init {
            onIntent(RecentExhibitionsIntent.Initialize)
        }

        fun onIntent(intent: RecentExhibitionsIntent) {
            when (intent) {
                is RecentExhibitionsIntent.Initialize -> {
                    loadExhibitions()
                }
                RecentExhibitionsIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(RecentExhibitionsEffect.NavigateBack) }
                }

                is RecentExhibitionsIntent.ExhibitionClicked -> {
                    viewModelScope.launch { _effect.emit(RecentExhibitionsEffect.NavigateToExhibitionDetail(intent.exhibitId)) }
                }
            }
        }

        private fun loadExhibitions() {
            viewModelScope.launch {
                getUserRecentExhibitionsUseCase(
                    imageQueryParams = RECENT_THUMB_QUERY,
                ).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            val exhibitions = result.data

                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    exhibitions = exhibitions,
                                )
                            }
                        }
                        is ApiResult.Error -> {
                            Log.d("RecentExhibitions", "${result.error}")
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
