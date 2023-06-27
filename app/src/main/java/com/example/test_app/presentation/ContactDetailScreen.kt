package com.example.test_app.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_app.domain.contact.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetail(
    navController: NavController,
    viewModel: ContactViewModel,
    id: String
){
    val state = viewModel.state
    state.contact?.let { data ->
        var firstName by remember { mutableStateOf(TextFieldValue(data.firstName)) }
        var lastName by remember { mutableStateOf(TextFieldValue(data.lastName)) }
        var email by remember { mutableStateOf(TextFieldValue(data.email ?: "")) }
        var dob by remember { mutableStateOf(TextFieldValue(data.dob ?: "")) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
                    actions = {
                        TextButton(
                            onClick = { viewModel.updateContact(
                                data.id, Contact(
                                    id = data.id,
                                    firstName = firstName.text,
                                    lastName =lastName.text,
                                    email = email.text,
                                    dob = dob.text
                                ))
                                navController.popBackStack()
                            }
                        ) {
                            Text("Save")
                        }
                    },
                    navigationIcon = {
                        TextButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Text("Cancel")
                        }
                    },
                    title = {Text("",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally))
                    },
                )
            },
            content = { padding ->
                Box(
                    modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "contactAvatar",
                        modifier = Modifier
                            .size(112.dp)
                            .background(Color.Black, CircleShape)
                            .align(CenterHorizontally)
                            .padding(6.dp),

                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Main Informtaion", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = firstName,
                        label = { Text("First Name") },
                        onValueChange = { newText ->
                            firstName = newText
                        }
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = lastName,
                        label = { Text("Last Name") },
                        onValueChange = { newText ->
                            lastName = newText
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Sub Informtaion", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        label = { Text("Email") },
                        onValueChange = { newText ->
                            email = newText
                        }
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dob,
                        label = { Text("Date Of Birth") },
                        onValueChange = { newText ->
                            dob = newText
                        }
                    )
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
                }
            }
        )

    }

}