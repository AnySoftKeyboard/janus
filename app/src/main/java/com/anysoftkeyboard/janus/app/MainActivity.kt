package com.anysoftkeyboard.janus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anysoftkeyboard.janus.app.ui.theme.JanusTheme

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
              composable(TabScreen.Translate.route) { TranslateScreen() }
              composable(TabScreen.History.route) { HistoryScreen() }
              composable(TabScreen.Bookmarks.route) { BookmarksScreen() }
            }
      }
}

enum class TabScreen(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  Translate("translate", "Translate", Icons.Default.Translate),
  History("history", "History", Icons.Default.History),
  Bookmarks("bookmarks", "Bookmarks", Icons.Default.Favorite)
}

@Composable
fun TranslateScreen() {
  Surface(modifier = Modifier.fillMaxSize()) { Column { Text("Translate Screen Content") } }
}

@Composable
fun HistoryScreen() {
  Surface(modifier = Modifier.fillMaxSize()) { Column { Text("History Screen Content") } }
}

@Composable
fun BookmarksScreen() {
  Surface(modifier = Modifier.fillMaxSize()) { Column { Text("Bookmarks Screen Content") } }
}

@Preview(showBackground = true)
@Composable
fun JanusAppPreview() {
  JanusTheme { JanusApp() }
}
