package com.anysoftkeyboard.janus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anysoftkeyboard.janus.app.ui.BookmarksScreen
import com.anysoftkeyboard.janus.app.ui.HistoryScreen
import com.anysoftkeyboard.janus.app.ui.TranslateScreen
import com.anysoftkeyboard.janus.app.ui.theme.JanusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    val initialText =
        if (savedInstanceState == null &&
            intent?.action == android.content.Intent.ACTION_PROCESS_TEXT) {
          intent?.getCharSequenceExtra(android.content.Intent.EXTRA_PROCESS_TEXT)?.toString()
        } else {
          null
        }
    setContent { JanusTheme { JanusApp(initialText) } }
  }
}

@Composable
fun JanusApp(initialText: String? = null) {
  val navController = rememberNavController()
  var selectedTab by remember { mutableStateOf(TabScreen.Translate) }

  Scaffold(
      bottomBar = {
        NavigationBar {
          TabScreen.entries.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.titleRes)) },
                selected = selectedTab == screen,
                onClick = {
                  selectedTab = screen
                  navController.navigate(screen.route) {
                    // Build back stack by only popping up to start destination
                    // This allows back button to navigate through tab history
                    popUpTo(TabScreen.Translate.route) {
                      inclusive = false
                      saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                  }
                })
          }
        }
      }) { innerPadding ->
        NavHost(
            navController,
            startDestination = TabScreen.Translate.route,
            Modifier.padding(innerPadding)) {
              composable(TabScreen.Translate.route) {
                TranslateScreen(hiltViewModel(), initialText)
              }
              composable(TabScreen.History.route) { HistoryScreen(hiltViewModel()) }
              composable(TabScreen.Bookmarks.route) { BookmarksScreen(hiltViewModel()) }
            }
      }
}

enum class TabScreen(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector,
) {
  Translate("translate", R.string.tab_translate, Icons.Default.Translate),
  History("history", R.string.tab_history, Icons.Default.History),
  Bookmarks("bookmarks", R.string.tab_bookmarks, Icons.Default.Favorite)
}

@Preview(showBackground = true)
@Composable
fun JanusAppPreview() {
  JanusTheme { JanusApp() }
}
