package com.lkonlesoft.wally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Search
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
                    MainContext()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContext() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navController = rememberNavController()
    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerBar(drawerState = drawerState, scope = scope, navController = navController)
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
                                imageVector = Icons.TwoTone.Search, contentDescription = "Search"
                            )
                        }
                    },
                    modifier = Modifier.padding(4.dp)
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = navController)
                }
            },
            bottomBar = {
                NavBar(navController = navController)
            }
        )
    }

}

@Composable
private fun DrawerMenuItem(
    imageVector: ImageVector,
    text: String,
    route: String,
    selected: Boolean,
    onItemClick: () -> Unit){
    val animateColor: Color by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0,
            easing = FastOutLinearInEasing
        ))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
            .background(
                animateColor,
                shape = RoundedCornerShape(20.dp)
            )
            ,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = route,
            modifier = Modifier.padding(5.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text,  modifier = Modifier.padding(5.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBar(drawerState: DrawerState, scope: CoroutineScope, navController: NavController){
    val drawerList = listOf(
        NavigationItem.Setting,
        NavigationItem.RateUs,
        NavigationItem.Donate,
        NavigationItem.About
    )
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
            items(drawerList) { item ->
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                DrawerMenuItem(
                    imageVector = item.icon,
                    text = item.tittle,
                    route = item.route,
                    selected = currentRoute == item.route,
                    onItemClick = {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                        scope.launch { drawerState.close() }
                    }
            )
            }
        }
    }
}


@Composable
fun NavBar(navController: NavController){
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favorite
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.route) },
                label = { Text(item.tittle) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "Favorites View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun SettingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "Settings View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun DonateScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "Donate View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun RateUsScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "Rate Us View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun AboutScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "About View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = "Home View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Favorite.route) {
            FavoriteScreen()
        }
        composable(NavigationItem.Setting.route) {
            SettingScreen()
        }
        composable(NavigationItem.About.route) {
            AboutScreen()
        }
        composable(NavigationItem.RateUs.route) {
            RateUsScreen()
        }
        composable(NavigationItem.Donate.route) {
            DonateScreen()
        }
    }
}

