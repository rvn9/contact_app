package com.example.test_app.navigation

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object Detail: Screens("detail")
    object AddContact: Screens("add_contact")
}