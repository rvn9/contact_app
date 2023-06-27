package com.example.test_app.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_app.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ContactViewModel
){
    val state = viewModel.state
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(onClick = {navController.navigate(Screens.AddContact.route) }) {
                        Icon(Icons.Filled.Add, null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.Search, null)
                    }
                },
                title = {Text("Contacts",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align= Alignment.CenterHorizontally))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)

            )
        },
        content = { padding -> Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)

        ) {
            state.contacts?.let { data ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(data) { contact ->
                        Card (
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(Screens.Detail.route + "/${contact.id}")
                                }
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "contactAvatar",
                                    modifier = Modifier
                                        .size(96.dp)
                                        .background(Color.White, CircleShape)
                                        .padding(6.dp),

                                    )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "${contact.firstName} ${contact.lastName}",
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            if(viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } },
    )
}