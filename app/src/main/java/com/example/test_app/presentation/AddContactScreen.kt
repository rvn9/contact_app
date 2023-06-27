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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.test_app.domain.contact.Contact
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    navController: NavController,
    viewModel: ContactViewModel,
){
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue( "")) }
    var dob by remember { mutableStateOf(TextFieldValue( "")) }
    val uuid = UUID.randomUUID()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
                actions = {
                    TextButton(
                        onClick = { viewModel.addContact(
                            Contact(
                                id = uuid.toString(),
                                firstName = firstName.text,
                                lastName =lastName.text,
                                email = email.text,
                                dob = dob.text
                            )
                        )
                            navController.popBackStack()
                        }
                    ) {
                        Text("Add")
                    }
                },

                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Cancel")
                    }
                },
                title = {
                    Text("Add Contact",
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
                            .align(Alignment.CenterHorizontally)
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
                        label = { Text("Last Name") },
                        value = lastName,
                        onValueChange = { newText ->
                            lastName = newText
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Sub Informtaion", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        value = email,
                        onValueChange = { newText ->
                            email = newText
                        }
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Date Of Birth") },
                        value = dob,
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