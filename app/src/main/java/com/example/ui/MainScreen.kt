package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.ConvertScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.KhmerNumberScreen
import com.example.ui.screens.PhrasesScreen
import com.example.ui.screens.SettingsScreen

enum class MainTab(
    val titleKhmer: String,
    val titleEnglish: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    CONVERT("បំលែង", "Convert", Icons.Filled.VolumeUp, Icons.Outlined.VolumeUp, "nav_tab_convert"),
    PHRASES("ឃ្លាគំរូ", "Phrases", Icons.Filled.FormatQuote, Icons.Outlined.FormatQuote, "nav_tab_phrases"),
    NUMBERS("លេខ", "Numbers", Icons.Filled.Numbers, Icons.Outlined.Numbers, "nav_tab_numbers"),
    HISTORY("ប្រវត្តិ", "History", Icons.Filled.History, Icons.Outlined.History, "nav_tab_history"),
    SETTINGS("កំណត់", "Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "nav_tab_settings")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TtsViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val snackMessage by viewModel.snackMessage.collectAsState()

    LaunchedEffect(snackMessage) {
        snackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackMessage()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    val currentTabItem = MainTab.entries[selectedTab]
                    Text(
                        text = "Text to សំឡេង • ${currentTabItem.titleKhmer}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                MainTab.entries.forEachIndexed { index, tab ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.titleEnglish
                            )
                        },
                        label = {
                            Text(
                                text = tab.titleKhmer,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.testTag(tab.testTag),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> ConvertScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
                1 -> PhrasesScreen(
                    viewModel = viewModel,
                    onSelectPhraseForEditor = { phraseText ->
                        viewModel.updateInputText(phraseText)
                        selectedTab = 0
                    },
                    modifier = Modifier.fillMaxSize()
                )
                2 -> KhmerNumberScreen(
                    viewModel = viewModel,
                    onSendToEditor = { numberText ->
                        viewModel.updateInputText(numberText)
                        selectedTab = 0
                    },
                    modifier = Modifier.fillMaxSize()
                )
                3 -> HistoryScreen(
                    viewModel = viewModel,
                    onUseInEditor = { text ->
                        viewModel.updateInputText(text)
                        selectedTab = 0
                    },
                    modifier = Modifier.fillMaxSize()
                )
                4 -> SettingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
