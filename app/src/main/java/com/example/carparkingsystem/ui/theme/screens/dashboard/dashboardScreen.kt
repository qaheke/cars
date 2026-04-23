package com.example.carparkingsystem.ui.theme.screens.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carparkingsystem.navigation.ROUTE_REGISTER
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    // Colors matching Dashboard
    val DarkBg = Color(0xFF0A0E17)
    val NeonCyan = Color(0xFF00F0FF)
    val NeonPurple = Color(0xFF7B2FF7)

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        delay(2000) // Wait for 2 seconds
        navController.navigate(ROUTE_REGISTER) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg),
        contentAlignment = Alignment.Center
    ) {
        // Glowing Background Circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(NeonPurple.copy(alpha = 0.15f), Color.Transparent),
                    center = center,
                    radius = 800f
                ),
                radius = 800f
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value).alpha(alpha.value)
        ) {
            // Creative Logo Placeholder (Icon in a Neon Box)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(NeonCyan, NeonPurple)),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBg, RoundedCornerShape(26.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Logo",
                        tint = NeonCyan,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PARK IT",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 4.sp
            )

            Text(
                text = "Smart Solutions for Smart Cities",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = NeonCyan.copy(alpha = 0.8f)
            )
        }

        // Bottom Loading Indicator
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .size(40.dp),
            color = NeonCyan,
            strokeWidth = 2.dp
        )
    }
}

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

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}
