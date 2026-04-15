package com.example.carparkingsystem.ui.theme.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    val selectedItem = remember { mutableStateOf(0) }

    // 🎨 NEON COLOR PALETTE
    val DarkBg = Color(0xFF0A0E17)
    val CardDarkBg = Color(0xFF161B2E)
    val NeonCyan = Color(0xFF00F0FF)
    val NeonPurple = Color(0xFF7B2FF7)
    val NeonPink = Color(0xFFFF0080)
    val NeonGreen = Color(0xFF00FF9D) // For "Available"

    // 🌈 GRADIENT for the bottom action buttons
    val thrillingGradient = Brush.horizontalGradient(listOf(NeonPurple, NeonPink))

    Scaffold(
        containerColor = DarkBg, // ✅ Dark background for the whole screen
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Parking Dashboard",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F1320)),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = NeonPink // ✅ Glowing logout icon
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0F1320)
            ) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NeonCyan,    // ✅ Active color
                        selectedTextColor = NeonCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent // Removes the default boring pill background
                    )
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("Search") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NeonCyan,
                        selectedTextColor = NeonCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = { selectedItem.value = 2 },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    label = { Text("Cart") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NeonCyan,
                        selectedTextColor = NeonCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedItem.value == 3,
                    onClick = { selectedItem.value = 3 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NeonCyan,
                        selectedTextColor = NeonCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // 🌟 TITLE WITH NEON CYAN
            Text(
                text = "Smart Parking System",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                modifier = Modifier.padding(bottom = 20.dp, top = 12.dp)
            )

            // ── SLOTS SUMMARY CARD (Dark Card with Neon Text) ──────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                // Subtle elevation helps dark cards stand out against dark backgrounds
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Available", color = Color.LightGray)
                        Text("18 Slots", fontSize = 20.sp, color = NeonGreen, fontWeight = FontWeight.Bold) // ✅ Green for available
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Occupied", color = Color.LightGray)
                        Text("32 Slots", fontSize = 20.sp, color = NeonPink, fontWeight = FontWeight.Bold) // ✅ Pink for occupied
                    }
                }
            }

            // ── TWO NEW CARDS (Dark with Neon accents) ─────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card 1 — My Bookings (Purple Accent)
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Bookings",
                            tint = NeonPurple, // ✅ Purple Icon
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("My Bookings", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("3 Active", color = NeonPurple, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }

                // Card 2 — Notifications (Cyan Accent)
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = NeonCyan, // ✅ Cyan Icon
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Alerts", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("2 New", color = NeonCyan, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // ── ACTION ROW (Neon Gradient Buttons) ─────────────────────
            // ✅ CHANGED: Using Box instead of Card so we can apply a Brush gradient
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(thrillingGradient, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Add Car", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(thrillingGradient, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Book Slot", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(thrillingGradient, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "View Cars", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    Dashboard(rememberNavController())
}