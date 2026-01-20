package com.arttrip.android.core.ui.launcher

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.core.net.toUri

private const val TAG = "WebLauncher"

@Composable
fun rememberWebLauncher(
    context: Context,
    onFailed: (() -> Unit)? = null,
): (String) -> Unit {
    val latestOnFailed by rememberUpdatedState(onFailed)

    return launcher@{ url: String ->
        val uri = runCatching { url.toUri() }.getOrNull()
        if (uri == null) {
            Log.w(TAG, "Invalid url: $url")
            latestOnFailed?.invoke()
            return@launcher
        }

        val customTabError =
            runCatching {
                CustomTabsIntent
                    .Builder()
                    .setShowTitle(true)
                    .setUrlBarHidingEnabled(true)
                    .build()
                    .launchUrl(context, uri)
            }.exceptionOrNull()

        if (customTabError == null) return@launcher

        Log.w(TAG, "CustomTabs failed. fallback ACTION_VIEW. url=$url", customTabError)

        val actionViewError =
            runCatching {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                )
            }.exceptionOrNull()

        if (actionViewError == null) return@launcher

        Log.e(TAG, "ACTION_VIEW failed too. url=$url", actionViewError)
        latestOnFailed?.invoke()
    }
}
