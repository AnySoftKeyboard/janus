package com.anysoftkeyboard.janus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
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
    super.onCreate(savedInstanceState)
    setContent { JanusTheme { JanusApp() } }
  }
}

@Composable
fun JanusApp() {
  val navController = rememberNavController()
  var selectedTab by remember { mutableStateOf(TabScreen.Translate) }

  Scaffold(
      bottomBar = {
        NavigationBar {
          TabScreen.entries.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = selectedTab == screen,
                onClick = {
                  selectedTab = screen
                  navController.navigate(screen.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
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
              composable(TabScreen.Translate.route) { TranslateScreen(hiltViewModel()) }
              composable(TabScreen.History.route) { HistoryScreen(hiltViewModel()) }
              composable(TabScreen.Bookmarks.route) { BookmarksScreen(hiltViewModel()) }
            }
      }
}

enum class TabScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
  Translate("translate", "Translate", Icons.Default.Translate),
  History("history", "History", Icons.Default.History),
  Bookmarks("bookmarks", "Bookmarks", Icons.Default.Favorite)
}

@Preview(showBackground = true)
@Composable
fun JanusAppPreview() {
  JanusTheme { JanusApp() }
}
