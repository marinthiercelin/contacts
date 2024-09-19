package ch.thiercelin.contacts.app.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestScreen(
    onPermissionsGranted:  @Composable () -> Unit
) {
    // Camera permission state
    val readContacts = rememberPermissionState(
        Manifest.permission.READ_CONTACTS
    )
    if (readContacts.status.isGranted) {
        onPermissionsGranted()
    } else {
        Column {
            val textToShow = if (readContacts.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "Reading the contacts is necessary for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Reading the contacts is required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { readContacts.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}