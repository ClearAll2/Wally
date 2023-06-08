package com.lkonlesoft.wally

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lkonlesoft.wally.ui.theme.WallyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WallyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContext(onAboutClick = {startAboutActivity()}, onSettingClick = {startSettingActivity()})
                }
            }
        }
    }
    private fun startSettingActivity(){
        val intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }
    private fun startAboutActivity(){
        val intent = Intent(this, About::class.java)
        startActivity(intent)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContext(onAboutClick: () -> Unit, onSettingClick: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerBar(drawerState = drawerState, scope = scope, onAboutClick = onAboutClick, onSettingClick = onSettingClick)
            }
        },
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu, contentDescription = "Menu"
                            )
                        }
                    },
                    title = { Text(text = "Wally", color = MaterialTheme.colorScheme.primary) },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        IconButton(onClick = { } ) {
                            Icon(
                                imageVector = Icons.TwoTone.Search, contentDescription = "Info"
                            )
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                )
            },
            bottomBar = {
                NavBar()
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Greeting(name = "Android")
            }
        }
    }

}

@Composable
private fun DrawerMenuItem(
    imageVector: ImageVector,
    text: String,
    onItemClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(25.dp))
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBar(drawerState: DrawerState, scope: CoroutineScope, onAboutClick: () -> Unit, onSettingClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {scope.launch { drawerState.close() }}) {
                Icon(
                    imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Back"
                )
            }
            Text(
                text = "Wally",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth())
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            item {DrawerMenuItem(imageVector = Icons.TwoTone.Settings, text = "Settings", onItemClick = onSettingClick)}
            item {DrawerMenuItem(imageVector = Icons.TwoTone.Star, text = "Rate us", onItemClick = {})}
            item {DrawerMenuItem(imageVector = Icons.TwoTone.ThumbUp, text = "Donate", onItemClick = {})}
            item {DrawerMenuItem(imageVector = Icons.TwoTone.Info, text = "About", onItemClick = onAboutClick)}
        }
    }
}


@Composable
fun NavBar(){
    var selectedItem by rememberSaveable{ mutableStateOf(0)}

    NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.TwoTone.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = selectedItem == 0,
                onClick = { selectedItem = 0 }
            )
            NavigationBarItem(
                icon = { Icon(Icons.TwoTone.Favorite, contentDescription = "Favorites") },
                label = { Text("Favorites") },
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WallyTheme {
        Greeting("Android")
    }
}