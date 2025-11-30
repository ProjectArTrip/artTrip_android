package com.arttrip.android.presentation.home

import androidx.lifecycle.ViewModel
import com.arttrip.android.domain.usecase.home.GetCountryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountryListUseCase: GetCountryListUseCase,
) : ViewModel() {
    // 여기부터 다시 UiState랑 ViewModel 엮고 StateFlow 만들기
}