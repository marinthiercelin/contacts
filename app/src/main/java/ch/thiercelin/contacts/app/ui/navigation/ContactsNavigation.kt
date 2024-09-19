package ch.thiercelin.contacts.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.thiercelin.contacts.app.data.model.ContactID
import ch.thiercelin.contacts.app.ui.navigation.ContactsDestinationsArgs.CONTACT_ID_ARG
import ch.thiercelin.contacts.app.ui.navigation.ContactsScreens.CONTACT_DETAILS_SCREEN
import ch.thiercelin.contacts.app.ui.navigation.ContactsScreens.CONTACT_LIST_SCREEN
import ch.thiercelin.contacts.app.ui.screen.contactdetails.ContactDetailsScreen
import ch.thiercelin.contacts.app.ui.screen.contactlist.ContactListScreen

/**
 * Screens used in [ContactsDestinations]
 */
private object ContactsScreens {
    const val CONTACT_LIST_SCREEN = "contacts"
    const val CONTACT_DETAILS_SCREEN = "contact"
}

/**
 * Arguments used in [ContactsDestinations] routes
 */
object ContactsDestinationsArgs {
    const val CONTACT_ID_ARG = "contactID"
}

/**
 * Destinations used in the [MainActivity]
 */
object ContactsDestinations {
    const val CONTACT_LIST_ROUTE = CONTACT_LIST_SCREEN
    const val CONTACT_DETAILS_ROUTE = "$CONTACT_DETAILS_SCREEN/{$CONTACT_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class ContactsNavigationActions(private val navController: NavHostController) {
    fun navigateToContactDetails(contactID: ContactID) {
        navController.navigate("$CONTACT_DETAILS_SCREEN/${contactID}")
    }
}

@Composable
fun ContactsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ContactsDestinations.CONTACT_LIST_ROUTE,
    navActions: ContactsNavigationActions = remember(navController) {
        ContactsNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            ContactsDestinations.CONTACT_LIST_ROUTE,
        ) {
            ContactListScreen(
                onContactClick = { contactID: ContactID -> navActions.navigateToContactDetails(contactID) }
            )
        }
        composable(
            ContactsDestinations.CONTACT_DETAILS_ROUTE
        ) {
            ContactDetailsScreen()
        }
    }
}