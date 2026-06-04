package com.example.trialig

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.core.app.NotificationManagerCompat

fun isNotificationListenerGranted(context: Context): Boolean {
    return NotificationManagerCompat
        .getEnabledListenerPackages(context)
        .contains(context.packageName)
}
@Composable
fun SetupScreen(
    activity: ComponentActivity,
    onContinue: () -> Unit
) {
    var notificationGranted by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    var listenerGranted by remember {
        mutableStateOf(isNotificationListenerGranted(activity))
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                listenerGranted = isNotificationListenerGranted(activity)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        notificationGranted = granted
    }

    var balance by remember { mutableStateOf("") }
    val balanceValid = balance.toDoubleOrNull() != null && balance.isNotBlank()

    val teal = Color(0xFF006D5B)
    val surface = Color(0xFF0F1C1A)
    val surfaceVariant = Color(0xFF1A2B28)
    val onSurface = Color(0xFFE0F2F1)
    val onSurfaceMuted = Color(0xFF80CBC4)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(surface)
    ) {
        // subtle gradient top accent
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            teal.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // App icon area
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(teal.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountBalanceWallet,
                    contentDescription = null,
                    tint = teal,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "PaymentGitHistory",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Let's get you set up",
                fontSize = 14.sp,
                color = onSurfaceMuted,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Permission buttons
            PermissionRow(
                icon = Icons.Rounded.Notifications,
                label = "Notification Permission",
                granted = notificationGranted,
                teal = teal,
                surfaceVariant = surfaceVariant,
                onSurface = onSurface,
                onSurfaceMuted = onSurfaceMuted,
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PermissionRow(
                icon = Icons.Rounded.NotificationsActive,
                label = "Notification Access",
                granted = listenerGranted, // check NotificationListenerService separately if needed
                teal = teal,
                surfaceVariant = surfaceVariant,
                onSurface = onSurface,
                onSurfaceMuted = onSurfaceMuted,
                onClick = {
                    activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Balance input
            OutlinedTextField(
                value = balance,
                onValueChange = { balance = it },
                label = {
                    Text(
                        "Current Bank Balance (₹)",
                        color = onSurfaceMuted,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Text(
                        "₹",
                        color = teal,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = teal,
                    unfocusedBorderColor = onSurfaceMuted.copy(alpha = 0.3f),
                    focusedTextColor = onSurface,
                    unfocusedTextColor = onSurface,
                    cursorColor = teal
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Continue button
            Button(
                onClick = {
                    val rootBalance = balance.toDoubleOrNull() ?: return@Button
                    CoroutineScope(Dispatchers.IO).launch {
                        DatabaseProvider
                            .getDatabase(activity)
                            .transactionDao()
                            .insertTransaction(
                                TransactionNode(
                                    amount = rootBalance,
                                    type = "ROOT",
                                    message = "Initial Balance",
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                    }
                    onContinue()
                },
                enabled = balanceValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = teal,
                    disabledContainerColor = teal.copy(alpha = 0.3f),
                    contentColor = Color.White,
                    disabledContentColor = Color.White.copy(alpha = 0.4f)
                )
            ) {
                Text(
                    text = "Continue",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.3.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PermissionRow(
    icon: ImageVector,
    label: String,
    granted: Boolean,
    teal: Color,
    surfaceVariant: Color,
    onSurface: Color,
    onSurfaceMuted: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (granted) teal else onSurfaceMuted,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = onSurface
                )
                Text(
                    text = if (granted) "Granted" else "Required",
                    fontSize = 11.sp,
                    color = if (granted) teal else onSurfaceMuted.copy(alpha = 0.7f)
                )
            }
        }

        if (granted) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(teal.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = teal,
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            TextButton(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Grant",
                    color = teal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}