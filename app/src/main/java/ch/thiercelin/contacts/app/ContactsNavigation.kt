package ch.thiercelin.contacts.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.thiercelin.contacts.app.ContactsScreens.CONTACT_LIST_SCREEN
import ch.thiercelin.contacts.app.contactlist.ContactListScreen

/**
 * Screens used in [ContactsDestinations]
 */
private object ContactsScreens {
    const val CONTACT_LIST_SCREEN = "contacts"
}

/**
 * Destinations used in the [MainActivity]
 */
object ContactsDestinations {
    const val CONTACT_LIST_ROUTE = CONTACT_LIST_SCREEN
}



@Composable
fun ContactsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ContactsDestinations.CONTACT_LIST_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            ContactsDestinations.CONTACT_LIST_ROUTE,
        ) {
            ContactListScreen()
        }
    }
}