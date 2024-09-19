package ch.thiercelin.contacts.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ch.thiercelin.contacts.app.ui.screen.PermissionRequestScreen
import ch.thiercelin.contacts.app.ui.navigation.ContactsNavGraph
import ch.thiercelin.contacts.app.ui.theme.ContactsAndroidTheme

@Composable
fun App() {
    ContactsAndroidTheme {
        PermissionRequestScreen {
            ContactsNavGraph()
        }
    }
}