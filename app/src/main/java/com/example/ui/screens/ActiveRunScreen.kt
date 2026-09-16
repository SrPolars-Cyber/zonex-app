package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ZonexRepository
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexButton
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexGemRed
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreenAccent
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedCrimson
import com.example.ui.theme.ZonexRedPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun ActiveRunScreen(
    onConcludeRun: () -> Unit,
    onExitRun: () -> Unit
) {
    val runStats by ZonexRepository.runStats.collectAsState()
    val userProfile by ZonexRepository.userProfile.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }
    var isAudioEnabled by remember { mutableStateOf(true) }
    var isScreenLocked by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Real-time Demo GPS Simulation ticker
    LaunchedEffect(runStats.isRunning, runStats.isPaused) {
        while (isActive && runStats.isRunning && !runStats.isPaused) {
            delay(1000L)
            ZonexRepository.tickRunSimulation()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZonexPureBlack)
    ) {
        // Satellite Map Background
        Image(
            painter = painterResource(id = R.drawable.img_city_map_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Vignette
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xDD060608),
                            Color(0x33060608),
                            Color(0xEE060608),
                            Color(0xFF060608)
                        )
                    )
                )
        )

        // Live GPS Path & Route on Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // GPS Running Path
            val gpsRoute = Path().apply {
                moveTo(w * 0.48f, h * 0.65f)
                lineTo(w * 0.49f, h * 0.58f)
                lineTo(w * 0.46f, h * 0.52f)
                lineTo(w * 0.52f, h * 0.45f)
                lineTo(w * 0.50f, h * 0.38f)
                lineTo(w * 0.55f, h * 0.32f)
            }

            // Route Glow & Line
            drawPath(
                path = gpsRoute,
                color = Color(0x66FF1E27),
                style = Stroke(width = 10f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            drawPath(
                path = gpsRoute,
                color = ZonexRedPrimary,
                style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            // Conquered area shadow behind route
            val conquestArea = Path().apply {
                moveTo(w * 0.42f, h * 0.48f)
                lineTo(w * 0.62f, h * 0.38f)
                lineTo(w * 0.58f, h * 0.30f)
                lineTo(w * 0.44f, h * 0.34f)
                close()
            }
            drawPath(conquestArea, color = Color(0x44FF1E27))
            drawPath(conquestArea, color = ZonexRedPrimary, style = Stroke(width = 2f))
        }

        // Screen Structure
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // TOP BAR & STATUS
            Column {
                // Top Action Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFF331418), RoundedCornerShape(8.dp))
                                .background(Color(0xFF14080B))
                                .clickable { showExitDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "CORRIDA EM ANDAMENTO",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "ZONEX CITY",
                                color = ZonexRedPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // SAIR Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.dp, Color(0xFF4A1016), RoundedCornerShape(6.dp))
                            .background(Color(0xFF1C060A))
                            .clickable { showExitDialog = true }
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SAIR",
                            color = ZonexRedPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Subheader Chips: User Profile, Safe Zone, Currencies
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Safe Zone Chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xE60D1A10))
                            .border(1.dp, Color(0xFF1B4D24), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(ZonexGreenAccent)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "ZONA SEGURA - Ativo ${String.format("%02d:%02d", runStats.safeZoneSecondsRemaining / 60, runStats.safeZoneSecondsRemaining % 60)}",
                            color = ZonexGreenAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Currencies
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        // ZX
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF17130A))
                                .padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("ZX ", color = ZonexGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            Text("${userProfile.zxCoins}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        // Gems
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E0A0E))
                                .padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Diamond, contentDescription = null, tint = ZonexGemRed, modifier = Modifier.size(10.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("${userProfile.gems}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // MIDDLE MAP FLOATING CONTROLS
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Runner Heading Pin
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .border(2.dp, ZonexRedPrimary, CircleShape)
                            .background(Color(0x33FF1E27))
                    )
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_avatar_runner),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Floating Buttons Right
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RunMapFloatIcon(
                        icon = if (isAudioEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.Default.VolumeOff,
                        onClick = {
                            isAudioEnabled = !isAudioEnabled
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(if (isAudioEnabled) "Áudio de orientação ativado" else "Áudio desativado")
                            }
                        }
                    )
                    RunMapFloatIcon(Icons.Default.LocationSearching) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("GPS centralizado no corredor") }
                    }
                    RunMapFloatIcon(Icons.Default.Layers) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("Camada territorial alternada") }
                    }
                    RunMapFloatIcon(Icons.Default.CenterFocusStrong) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("Modo mira de conquista ativado") }
                    }
                }

                // Floating chip left: SEU TERRITÓRIO
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 14.dp, top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xD90C0C14))
                        .border(1.dp, ZonexRedPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🚩 SEU TERRITÓRIO (+6 células)",
                        color = ZonexRedPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // BOTTOM RUNNING HUD PANEL
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color(0xF00A0A10))
                    .border(1.dp, Color(0xFF2C1014), RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                // Primary Metrics Row: DISTÂNCIA | TEMPO | RITMO MÉDIO | VELOCIDADE | CALORIAS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LiveMetricItem(
                        value = String.format("%.2f", runStats.distanceKm),
                        unit = "KM",
                        label = "DISTÂNCIA",
                        tint = Color.White
                    )
                    LiveMetricItem(
                        value = ZonexRepository.formatSecondsToHhMmSs(runStats.elapsedSeconds),
                        unit = "",
                        label = "TEMPO",
                        tint = Color.White
                    )
                    LiveMetricItem(
                        value = runStats.paceMinutesPerKm,
                        unit = "MIN/KM",
                        label = "RITMO MÉD.",
                        tint = Color.White
                    )
                    LiveMetricItem(
                        value = String.format("%.1f", runStats.speedKmh),
                        unit = "KM/H",
                        label = "VELOCIDADE",
                        tint = Color.White
                    )
                    LiveMetricItem(
                        value = "${runStats.caloriesBurned}",
                        unit = "KCAL",
                        label = "CALORIAS",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Heart Rate Bar (BPM & Zone 1-5 Indicator)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF14141E))
                        .border(1.dp, Color(0xFF241418), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = ZonexRedPrimary,
                            modifier = Modifier
                                .size(16.dp)
                                .scale(pulseScale)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${runStats.heartBpm} BPM",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ZONA 4 (Anaeróbica)",
                            color = ZonexRedPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Zone 1 to 5 Color Indicators
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        ZonePill(Color(0xFF38BDF8), false) // Blue
                        ZonePill(Color(0xFF22C55E), false) // Green
                        ZonePill(Color(0xFFF59E0B), false) // Yellow
                        ZonePill(Color(0xFFFF5722), true)  // Orange active
                        ZonePill(Color(0xFFEF4444), false) // Red
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Controls Row: Lock | Pause/Resume | CONCLUIR CORRIDA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Lock Button
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color(0xFF331418), RoundedCornerShape(10.dp))
                            .background(Color(0xFF14080B))
                            .clickable {
                                isScreenLocked = !isScreenLocked
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(if (isScreenLocked) "Tela bloqueada para corrida" else "Tela desbloqueada")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isScreenLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                            contentDescription = "Trava de Tela",
                            tint = if (isScreenLocked) ZonexRedPrimary else ZonexGreyLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Pause / Resume Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color(0xFF4A1016), RoundedCornerShape(10.dp))
                            .background(Color(0xFF1A0A0E))
                            .clickable {
                                if (runStats.isPaused) {
                                    ZonexRepository.resumeRunSession()
                                } else {
                                    ZonexRepository.pauseRunSession()
                                }
                            }
                            .testTag("run_btn_pause_resume"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (runStats.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                contentDescription = null,
                                tint = ZonexGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (runStats.isPaused) "RETOMAR" else "PAUSAR",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // CONCLUIR CORRIDA Button
                    Box(
                        modifier = Modifier
                            .weight(1.3f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.5.dp, Color(0xFFFF6670), RoundedCornerShape(10.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        ZonexRedPrimary,
                                        ZonexRedCrimson,
                                        Color(0xFF7A040B)
                                    )
                                )
                            )
                            .clickable(onClick = onConcludeRun)
                            .testTag("run_btn_conclude"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CONCLUIR",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }

        // Exit Confirmation Dialog
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = {
                    Text("ENCERRAR SESSÃO?", color = ZonexRedPrimary, fontWeight = FontWeight.Black)
                },
                text = {
                    Text(
                        "Deseja descartar os dados desta corrida e voltar ao mapa principal?",
                        color = ZonexGreyLight,
                        fontSize = 13.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        onExitRun()
                    }) {
                        Text("SIM, SAIR", color = ZonexRedPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text("CONTINUAR CORRENDO", color = ZonexGreyMuted)
                    }
                },
                containerColor = ZonexCardSurface
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
        )
    }
}

@Composable
private fun LiveMetricItem(
    value: String,
    unit: String,
    label: String,
    tint: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                color = tint,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
            )
            if (unit.isNotEmpty()) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = unit,
                    color = ZonexGreyMuted,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = label,
            color = ZonexGreyMuted,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun ZonePill(color: Color, isActive: Boolean) {
    Box(
        modifier = Modifier
            .width(16.dp)
            .height(8.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color)
            .border(
                if (isActive) 1.5.dp else 0.dp,
                if (isActive) Color.White else Color.Transparent,
                RoundedCornerShape(3.dp)
            )
    )
}

@Composable
private fun RunMapFloatIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xE60C0C14))
            .border(1.dp, Color(0xFF2C1014), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ZonexRedPrimary,
            modifier = Modifier.size(20.dp)
        )
    }
}
