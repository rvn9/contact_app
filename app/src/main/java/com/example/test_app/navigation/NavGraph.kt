package com.example.test_app.navigation

import androidx.compose.runtime.Composable
import com.example.test_app.presentation.HomeScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.test_app.presentation.ContactDetail
import com.example.test_app.presentation.ContactViewModel

@Composable
fun NavGraph (navController: NavHostController, viewModel: ContactViewModel){
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    )
    {
        composable(route = Screens.Home.route)
        {
            HomeScreen(navController, viewModel)
        }
        composable(route = Screens.Detail.route + "/{contactId}",   arguments = listOf(navArgument("contactId") { type = NavType.StringType }))
        {
            val contactId = it.arguments?.getString("contactId")
            if(contactId != null)
            ContactDetail(navController, viewModel,contactId)
        }
    }
}