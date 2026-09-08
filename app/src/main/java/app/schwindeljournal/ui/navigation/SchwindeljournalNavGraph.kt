package app.schwindeljournal.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.schwindeljournal.ui.einstellungen.EinstellungenScreen
import app.schwindeljournal.ui.journalverlauf.JournalVerlaufScreen
import app.schwindeljournal.ui.onboarding.OnboardingScreen
import app.schwindeljournal.ui.schnellerfassung.SchnellErfassungScreen
import app.schwindeljournal.ui.shared.ModusViewModel
import app.schwindeljournal.ui.steckbrief.SteckbriefScreen
import app.schwindeljournal.ui.uebungsbegleiter.UebungsBegleiterScreen
import app.schwindeljournal.ui.wissensbibliothek.WissensBibliothekScreen

/**
 * Wurzel der Navigation. `modusViewModel` wird hier einmalig auf Activity-Ebene
 * erzeugt (hiltViewModel() ausserhalb jedes NavHost-Destinations-Scopes) und
 * explizit an alle Screens weitergereicht – das ist die "eine, zentrale Quelle"
 * fuer den Modus (roadmap-schwindeljournal-app.md, Phase 0.5), keine verstreuten
 * Einzelabfragen pro Screen.
 */
@Composable
fun SchwindeljournalNavGraph(
    navController: NavHostController = rememberNavController(),
    modusViewModel: ModusViewModel = hiltViewModel(),
) {
    val hasProfile by modusViewModel.hasProfile.collectAsStateWithLifecycle()

    when (hasProfile) {
        null -> LadeAnzeige()
        false -> OnboardingScreen(onModusGewaehlt = modusViewModel::onModusGewaehlt)
        true -> HauptShell(navController = navController, modusViewModel = modusViewModel)
    }
}

@Composable
private fun LadeAnzeige() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HauptShell(
    navController: NavHostController,
    modusViewModel: ModusViewModel,
) {
    val modus by modusViewModel.modus.collectAsStateWithLifecycle()
    val ampelHoherKontrast by modusViewModel.ampelHoherKontrast.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { HauptBottomNavigation(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.SchnellErfassung.route,
            modifier = Modifier.padding(innerPadding),
            // Keine Screen-Transition-Animationen (UX-Leitplanke: keine unnoetigen
            // Animationen, Zielgruppe kann bewegungsempfindlich sein).
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable(Destination.SchnellErfassung.route) {
                SchnellErfassungScreen(modus, ampelHoherKontrast)
            }
            composable(Destination.JournalVerlauf.route) {
                JournalVerlaufScreen(modus, ampelHoherKontrast)
            }
            composable(Destination.WissensBibliothek.route) { WissensBibliothekScreen(modus) }
            composable(Destination.UebungsBegleiter.route) { UebungsBegleiterScreen(modus) }
            composable(Destination.Einstellungen.route) {
                EinstellungenScreen(
                    aktuellerModus = modus,
                    onModusWechsel = modusViewModel::onModusGewechselt,
                    onSteckbriefOeffnen = { navController.navigate(Destination.Steckbrief.route) },
                )
            }
            composable(Destination.Steckbrief.route) {
                SteckbriefScreen(onZurueck = { navController.popBackStack() })
            }
        }
    }
}

@Composable
private fun HauptBottomNavigation(navController: NavHostController) {
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.destination.route,
                onClick = {
                    navController.navigate(item.destination.route) {
                        popUpTo(Destination.SchnellErfassung.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
            )
        }
    }
}
