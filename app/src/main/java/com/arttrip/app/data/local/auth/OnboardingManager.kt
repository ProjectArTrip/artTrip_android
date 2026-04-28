package com.arttrip.app.data.local.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arttrip.app.domain.model.auth.OnboardingStep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val KEY_STEP = stringPreferencesKey("onboarding_step")

@Singleton
class OnboardingManager
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) {
        private val scope = CoroutineScope(Dispatchers.IO)

        @Volatile
        private var cachedStep: OnboardingStep? = null

        init {
            val prefs = runBlocking { dataStore.data.first() }
            cachedStep = prefs[KEY_STEP]?.let { runCatching { OnboardingStep.valueOf(it) }.getOrNull() }
        }

        fun get(): OnboardingStep? = cachedStep

        fun save(step: OnboardingStep) {
            cachedStep = step
            scope.launch { dataStore.edit { it[KEY_STEP] = step.name } }
        }

        fun clear() {
            cachedStep = null
            scope.launch { dataStore.edit { it.remove(KEY_STEP) } }
        }
    }
