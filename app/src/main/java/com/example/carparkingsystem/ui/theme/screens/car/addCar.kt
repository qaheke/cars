package com.example.carparkingsystem.ui.theme.screens.car

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.carparkingsystem.data.AuthViewModel
import com.example.carparkingsystem.data.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(navController: NavController) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var plateNumber by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var driverName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var carColor by remember { mutableStateOf("") }
    var entryTime by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val carViewModel: CarViewModel = viewModel()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Car Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Cyan,
                    titleContentColor = Color.Red
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // IMAGE SECTION
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Button(onClick = { launcher.launch("image/*") }) {
                Text("Select Image")
            }

            // PLATE NUMBER
            OutlinedTextField(
                value = plateNumber,
                onValueChange = { plateNumber = it },
                label = { Text("Plate Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // VEHICLE TYPE
            OutlinedTextField(
                value = vehicleType,
                onValueChange = { vehicleType = it },
                label = { Text("Vehicle Type") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // DRIVER NAME
            OutlinedTextField(
                value = driverName,
                onValueChange = { driverName = it },
                label = { Text("Driver Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // PHONE NUMBER
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = carColor,
                onValueChange = { carColor = it },
                label = { Text("Car Color") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = entryTime,
                onValueChange = { entryTime = it },
                label = { Text("Entry Time") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // SAVE BUTTON
            Button(
                onClick = {
                    carViewModel.uploadCar(
                        imageUri = imageUri,
                        plateNumber = plateNumber,
                        vehicleType = vehicleType,
                        driverName = driverName,
                        phoneNumber = phoneNumber,
                        carColor = carColor,
                        entryTime = entryTime,
                        context = context,
                        navController = navController
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text("Save Car")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddCarScreenPreview() {
    AddCarScreen(rememberNavController())
}
