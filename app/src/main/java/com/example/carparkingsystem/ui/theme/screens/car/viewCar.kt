package com.example.carparkingsystem.ui.theme.screens.car

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.carparkingsystem.data.CarViewModel
import com.example.carparkingsystem.models.CarModel
import com.example.carparkingsystem.navigation.ROUTE_UPDATE_CAR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCarsScreen(navController: NavController) {
    val viewModel: CarViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchCar(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parked Cars", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF16213E))
            )
        },
        containerColor = Color(0xFF0D0D0D)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (viewModel.cars.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No cars found", color = Color.Gray)
                }
            } else {
                LazyColumn {
                    items(viewModel.cars) { car ->
                        CarItem(car, navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CarItem(car: CarModel, navController: NavController, viewModel: CarViewModel) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = car.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = car.plateNumber ?: "Unknown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "Driver: ${car.driverName}", color = Color.LightGray)
                Text(text = "Type: ${car.vehicleType}", color = Color.LightGray)
                Text(text = "Entry: ${car.entryTime}", color = Color.LightGray)
            }

            Column {
                IconButton(onClick = {
                    navController.navigate("${ROUTE_UPDATE_CAR}/${car.id}")
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Cyan)
                }
                IconButton(onClick = {
                    car.id?.let { viewModel.deleteCar(it, context) }
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}
