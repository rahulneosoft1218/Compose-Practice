package rahul.co.neosoft.compose.ui.theme.Drawer

import rahul.co.neosoft.compose.R

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Music : NavDrawerItem("music", R.drawable.ic_profile, "Contacts")
    object Movies : NavDrawerItem("movies", R.drawable.ic_sensor, "Proximity Sensor")
    object Books : NavDrawerItem("books", R.drawable.ic_baseline_fingerprint, "Biometric Verification")
    object Profile : NavDrawerItem("other", R.drawable.ic_profile, "Custom View")
    object Settings : NavDrawerItem("settings", R.drawable.ic_qr_code, "QR Scan")
}
