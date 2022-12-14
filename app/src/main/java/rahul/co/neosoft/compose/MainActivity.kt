package rahul.co.neosoft.compose

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import rahul.co.neosoft.compose.other.PowerConnectionReceiver
import rahul.co.neosoft.compose.presentation.contact.QRScan.QRCodeScannerView
import rahul.co.neosoft.compose.presentation.contact.create.CreateContactView
import rahul.co.neosoft.compose.presentation.contact.create.CreateContactViewModel
import rahul.co.neosoft.compose.presentation.contact.employee.EmployeeView
import rahul.co.neosoft.compose.presentation.contact.employee.EmployeeViewModel
import rahul.co.neosoft.compose.presentation.contact.list.*
import rahul.co.neosoft.compose.presentation.contact.open.OpenContactView
import rahul.co.neosoft.compose.presentation.contact.open.OpenContactViewModel
import rahul.co.neosoft.compose.presentation.contact.open.OpenProfileView
import rahul.co.neosoft.compose.ui.theme.Drawer.NavDrawerItem


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen()
//            initSensor()

            /* val navController = rememberNavController()
             CleanContactsTheme {
                 Router(navController, this)
             }
             askPermission()
             registerBroadcastRx()*/
        }
    }

    @Preview
    @Composable
    fun MainScreen() {
        val scaffoldState = rememberScaffoldState(drawerState = rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()
        // If you want the drawer from the right side, uncomment the following
        // CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
            drawerBackgroundColor = colorResource(id = R.color.purple_200),
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
            },
            backgroundColor = colorResource(id = R.color.purple_500)
        ) { padding ->  // We need to pass scaffold's inner padding to content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        }
        // }
    }


    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }

    @Composable
    fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "")
                }
            },
            backgroundColor = colorResource(id = R.color.purple_200),
            contentColor = Color.White
        )
    }

    @Preview(showBackground = false)
    @Composable
    fun TopBarPreview() {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        TopBar(scope = scope, scaffoldState = scaffoldState)
    }

    @Composable
    fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
        val items = listOf(
            NavDrawerItem.Home,
            NavDrawerItem.Music,
            NavDrawerItem.Movies,
            NavDrawerItem.Books,
            NavDrawerItem.Profile,
            NavDrawerItem.Settings
        )
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.purple_700))
        ) {
            // Header
            Image(
                painter = painterResource(id = R.drawable.me),
                contentDescription = "Name",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RectangleShape)
            )
            // Space between
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            // List of navigation items
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
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
                    // Close drawer
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Exploring Compose",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    @Composable
    fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
        val background = if (selected) R.color.purple_500 else android.R.color.transparent
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(item) })
                .height(45.dp)
                .background(colorResource(id = background))
                .padding(start = 10.dp)
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.title,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
    @Composable
    fun HomeScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Home View",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        HomeScreen()
    }

    lateinit var proximitySensor: Sensor
    lateinit var sensorManager: SensorManager
    var sensorStatus:String = "Start"

    @Composable
    fun MoviesScreen() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.purple_500))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = sensorStatus,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MoviesScreenPreview() {
        MoviesScreen()
    }


    @Composable
    fun BooksScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.purple_500))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Books View",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun BooksScreenPreview() {
        BooksScreen()
    }

    @Composable
    fun ProfileScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.purple_500))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Profile View",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ProfileScreenPreview() {
        ProfileScreen()
    }

    @Composable
    fun SettingsScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.purple_500))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Settings View",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SettingsScreenPreview() {
        SettingsScreen()
    }
    @Preview(showBackground = false)
    @Composable
    fun DrawerItemPreview() {
        DrawerItem(item = NavDrawerItem.Home, selected = false, onItemClick = {})
    }
    @Preview(showBackground = true)
    @Composable
    fun DrawerPreview() {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val navController = rememberNavController()
        Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
    }
    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = "splash_screen") {
            composable("splash_screen"){
                SplashScreen(navController = navController)
            }
            composable(NavDrawerItem.Home.route) {
                HomeScreen()
            }
            composable(NavDrawerItem.Music.route) {
                val listContactsViewModel: ListContactsViewModel = hiltViewModel()
                ContactListView(navController, listContactsViewModel)
            }
            composable(NavDrawerItem.Movies.route) {
                SensorView(navController, applicationContext)
            }
            composable(NavDrawerItem.Books.route) {
                BiometricView(navController, LocalContext.current)
            }
            composable(NavDrawerItem.Profile.route) {
                CustomeOverlappingView(this@MainActivity)
            }
            composable(NavDrawerItem.Settings.route) {
                QRCodeScannerView(this@MainActivity)
            }
            composable("list") {
                val listContactsViewModel: ListContactsViewModel = hiltViewModel()
                ContactListView(navController, listContactsViewModel)
            }
            composable("create") {
                val createContactsViewModel: CreateContactViewModel = hiltViewModel()
                CreateContactView(navController, createContactsViewModel)
            }
            composable("open"){
                val openContactViewModel: OpenContactViewModel = hiltViewModel()
                OpenContactView(navController = navController,openContactViewModel,this@MainActivity)
            }
            composable("profile"){
                OpenProfileView(navController = navController,this@MainActivity)
            }
            composable("employee"){
                val employeeViewModel: EmployeeViewModel = hiltViewModel()
                EmployeeView(navController = navController,employeeViewModel,this@MainActivity)
            }
        }
    }

    @Composable
    fun SplashScreen(navController: NavController) {
        val scale = remember {
            androidx.compose.animation.core.Animatable(0f)
        }

        // Animation
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                // tween Animation
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    }))
            // Customize the delay time
            delay(3000L)
            navController.navigate(NavDrawerItem.Home.route)
        }

        // Image
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(color = Color.White)) {
            // Change the logo
            Image(painter = painterResource(id = R.drawable.neo),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value))
        }
    }

    private fun registerBroadcastRx() {
        /*val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            registerReceiver(PowerConnectionReceiver(), ifilter)
        }*/

        val intent = Intent(this, PowerConnectionReceiver::class.java)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.action = "my.custom.broadcast"
        sendBroadcast(intent)
        callFlow()
    }

    private fun callFlow() {
        var flowOne = flowOf("Himanshu", "Amit", "Janishar").flowOn(Dispatchers.Default)
        var flowTwo = flowOf("Singh", "Shekhar", "Ali").flowOn(Dispatchers.Default)
        var flowThree = flowOf("Singh", "Shekhar", "Ali").flowOn(Dispatchers.Default)

        CoroutineScope(Dispatchers.Default).launch {
            flowOne.zip(flowTwo){
                    one,two -> "$one $two"
            }.flowOn(Dispatchers.Default).collect{
                Log.d("djfbvjvdhv", it[0].toString())
            }
        }

    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE),
                345)
        }
    }
}