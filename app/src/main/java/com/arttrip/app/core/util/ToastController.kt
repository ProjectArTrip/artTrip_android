import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ToastController(
    private val scope: CoroutineScope,
) {
    val hostState = SnackbarHostState()

    fun show(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ) {
        scope.launch {
            hostState.showSnackbar(message = message, duration = duration)
        }
    }

    fun dismiss() {
        hostState.currentSnackbarData?.dismiss()
    }
}
