package com.example.carparkingsystem.ui.theme.screens.register


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carparkingsystem.R
import com.example.carparkingsystem.data.AuthViewModel
import com.example.carparkingsystem.navigation.ROUTE_LOGIN

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current


    // Reusable white text field colors
    val whiteFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.Black,
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.Gray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo / avatar image
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .shadow(4.dp, CircleShape)
        )

        // Screen title
        Text(
            text = "Register Here",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        // Username field
        OutlinedTextField(
            modifier = Modifier.background(Color.Black),
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Enter your username") },
            placeholder = { Text(text = "Enter Username") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            colors = whiteFieldColors
        )



        // Email field
        OutlinedTextField(
            modifier = Modifier.background(Color.Black),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter your email") },
            placeholder = { Text(text = "Enter Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            colors = whiteFieldColors
        )

        // Password field
        OutlinedTextField(
            modifier = Modifier.background(Color.Black),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter your Password") },
            placeholder = { Text(text = "Enter Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            colors = whiteFieldColors
        )

        // Confirm Password field
        OutlinedTextField(
            modifier = Modifier.background(Color.Black),
            value = confirmpassword,
            onValueChange = { confirmpassword = it },
            label = { Text(text = "Confirm your Password") },
            placeholder = { Text(text = "Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            colors = whiteFieldColors
        )

        // Register button
        Button(onClick = { authViewModel.signup(username=username,
            email=email,password=password,
            confirmpassword=confirmpassword,
            navController=navController,
            context=context)}) {
            Text(text = "Register")
        }

        // Link to Login screen
        Row {
            Text(text = "Already have an account?", color = Color.Red)
            Text(text = " Login here", color = Color.Blue,
                modifier = Modifier.clickable{ navController.navigate(ROUTE_LOGIN)})
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}
