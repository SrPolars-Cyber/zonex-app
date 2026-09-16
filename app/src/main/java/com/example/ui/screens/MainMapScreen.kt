package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
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
import com.example.ui.components.ZonexBottomNav
import com.example.ui.components.ZonexNavTab
import com.example.ui.components.ZonexTopHeader
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreenAccent
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedCrimson
import com.example.ui.theme.ZonexRedPrimary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MainMapScreen(
    onNavigateToRun: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAvatar: () -> Unit,
    highlightConqueredTerritory: Boolean = false
) {
    val userProfile by ZonexRepository.userProfile.collectAsState()
    var showDialogInfo by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableStateOf(ZonexNavTab.MAPA) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulseAnimation")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZonexPureBlack)
    ) {
        // Satellite Cyber City Map Canvas & Image
        Image(
            painter = painterResource(id = R.drawable.img_city_map_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark Vignette
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.Transparent, Color(0x99060608), Color(0xDD060608)),
                        radius = 1200f
                    )
                )
        )

        // Interactive Territorial Overlay (Cyber Polygon Canvas)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // 1. Red Zone (Clan Territory / User)
            val redPolygon = Path().apply {
                moveTo(w * 0.45f, h * 0.25f)
                lineTo(w * 0.72f, h * 0.30f)
                lineTo(w * 0.65f, h * 0.42f)
                lineTo(w * 0.52f, h * 0.46f)
                lineTo(w * 0.30f, h * 0.44f)
                lineTo(w * 0.22f, h * 0.38f)
                lineTo(w * 0.35f, h * 0.28f)
                close()
            }
            drawPath(
                path = redPolygon,
                color = if (highlightConqueredTerritory) Color(0x66FF1E27) else Color(0x40FF1E27),
                style = Fill
            )
            drawPath(
                path = redPolygon,
                color = ZonexRedPrimary,
                style = Stroke(width = if (highlightConqueredTerritory) 4f else 2.5f)
            )

            // Draw internal cyber honeycomb cells inside red zone
            val cellRadius = 18f
            for (col in -2..2) {
                for (row in -2..2) {
                    val cx = w * 0.48f + col * 34f + (row % 2) * 17f
                    val cy = h * 0.36f + row * 28f
                    val hexPath = Path().apply {
                        for (i in 0..5) {
                            val angle = Math.PI / 3 * i
                            val px = (cx + cellRadius * cos(angle)).toFloat()
                            val py = (cy + cellRadius * sin(angle)).toFloat()
                            if (i == 0) moveTo(px, py) else lineTo(px, py)
                        }
                        close()
                    }
                    drawPath(hexPath, color = Color(0x33FF1E27), style = Fill)
                    drawPath(hexPath, color = Color(0x66FF4D55), style = Stroke(width = 1f))
                }
            }

            // 2. Yellow Zone (Top Left)
            val yellowPolygon = Path().apply {
                moveTo(w * 0.05f, h * 0.18f)
                lineTo(w * 0.32f, h * 0.19f)
                lineTo(w * 0.25f, h * 0.32f)
                lineTo(w * 0.08f, h * 0.31f)
                close()
            }
            drawPath(yellowPolygon, color = Color(0x33D97706), style = Fill)
            drawPath(yellowPolygon, color = Color(0xFFF59E0B), style = Stroke(width = 2f))

            // 3. Purple Zone (Top Right)
            val purplePolygon = Path().apply {
                moveTo(w * 0.68f, h * 0.20f)
                lineTo(w * 0.95f, h * 0.21f)
                lineTo(w * 0.90f, h * 0.35f)
                lineTo(w * 0.70f, h * 0.34f)
                close()
            }
            drawPath(purplePolygon, color = Color(0x339333EA), style = Fill)
            drawPath(purplePolygon, color = Color(0xFFA855F7), style = Stroke(width = 2f))

            // 4. Blue Zone (Bottom Left)
            val bluePolygon = Path().apply {
                moveTo(w * 0.08f, h * 0.46f)
                lineTo(w * 0.38f, h * 0.48f)
                lineTo(w * 0.30f, h * 0.64f)
                lineTo(w * 0.05f, h * 0.58f)
                close()
            }
            drawPath(bluePolygon, color = Color(0x332563EB), style = Fill)
            drawPath(bluePolygon, color = Color(0xFF3B82F6), style = Stroke(width = 2f))

            // 5. Green Zone (Bottom Right)
            val greenPolygon = Path().apply {
                moveTo(w * 0.60f, h * 0.47f)
                lineTo(w * 0.92f, h * 0.48f)
                lineTo(w * 0.88f, h * 0.64f)
                lineTo(w * 0.55f, h * 0.62f)
                close()
            }
            drawPath(greenPolygon, color = Color(0x3316A34A), style = Fill)
            drawPath(greenPolygon, color = Color(0xFF22C55E), style = Stroke(width = 2f))

            // Dotted Trail of runner from bottom toward center
            val trailPoints = listOf(
                Offset(w * 0.50f, h * 0.72f),
                Offset(w * 0.50f, h * 0.67f),
                Offset(w * 0.49f, h * 0.62f),
                Offset(w * 0.51f, h * 0.57f),
                Offset(w * 0.50f, h * 0.54f),
                Offset(w * 0.46f, h * 0.45f),
                Offset(w * 0.48f, h * 0.40f)
            )
            for (pt in trailPoints) {
                drawCircle(color = ZonexRedPrimary, radius = 4f, center = pt)
            }
        }

        // Main UI Overlay Layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top App Bar
            Column {
                ZonexTopHeader(
                    profile = userProfile,
                    onAvatarClick = onNavigateToProfile,
                    onNotificationsClick = {
                        showDialogInfo = "Você possui 2 novas notificações de clã e 1 recompensa de missão pronta para resgate!"
                    }
                )

                // Subheader Cards Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Territory Info Card (Left)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xE60F0F16))
                            .border(1.dp, Color(0xFF3B141A), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .clickable {
                                showDialogInfo = "ZONEX CITY (Centro): ${userProfile.territoriesCount} células dominadas. Próximo bônus de clã em 4 células."
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationCity,
                            contentDescription = null,
                            tint = ZonexRedPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "ZONEX CITY",
                                color = ZonexRedPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "SEU TERRITÓRIO",
                                color = ZonexGreyMuted,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${userProfile.territoriesCount} CÉLULAS",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = ZonexRedPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // 3 Quick Action Buttons (Right)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        // Missões
                        QuickActionBadge(
                            title = "MISSÕES",
                            badgeCount = 3,
                            icon = Icons.Default.TrackChanges
                        ) {
                            showDialogInfo = "Missões ativas:\n1. Conquiste 3 novas células hoje (2/3)\n2. Corra 5km em velocidade > 7.0 km/h (1/1 - Concluída!)\n3. Ajude seu clã a defender a Zona Central."
                        }

                        // Eventos
                        QuickActionBadge(
                            title = "EVENTOS",
                            badgeCount = null,
                            icon = Icons.Default.CalendarMonth
                        ) {
                            showDialogInfo = "Grande Torneio Zonex City começa em 2 dias! Participe para ganhar gemas e roupas lendárias."
                        }

                        // Clã
                        QuickActionBadge(
                            title = "CLÃ",
                            badgeCount = 2,
                            icon = Icons.Default.Group
                        ) {
                            showDialogInfo = "CLÃ ZX TEAM\nLíder: ZX_Shadow\nMembros: 24/30\nRanking atual: #3 na região."
                        }
                    }
                }
            }

            // Middle floating HUD elements
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Giant Red Clan Hologram Beacon Flag (in center of red zone)
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(ZonexRedPrimary)
                            .border(1.dp, Color.White, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("X", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(20.dp)
                            .background(ZonexRedPrimary)
                    )
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(glowAlpha)
                            .clip(CircleShape)
                            .background(Color(0x66FF1E27))
                    )
                }

                // Current Runner Position Marker with Avatar & Heading Pointer
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .scale(pulseScale)
                                .alpha(glowAlpha)
                                .clip(CircleShape)
                                .border(2.dp, ZonexRedPrimary, CircleShape)
                                .background(Color(0x33FF1E27))
                        )
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(2.dp, ZonexRedPrimary, CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_avatar_runner),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    // Directional arrow
                    Icon(
                        imageVector = Icons.Default.North,
                        contentDescription = null,
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Floating Map Controls (Right Side)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 14.dp, top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MapFloatIcon(Icons.Default.Explore, "Bússola", isRed = true)
                    MapFloatIcon(Icons.Default.Layers, "Camadas")
                    MapFloatIcon(Icons.Default.LocationSearching, "Centralizar")
                }

                // Floating Live Metrics Panels (Bottom left & bottom right)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 14.dp, end = 14.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Metrics Card (BPM, PASSOS, KCAL)
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xD90C0C14))
                            .border(1.dp, Color(0xFF2E1016), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MetricRow(icon = Icons.Default.Favorite, value = "138", label = "BPM", tint = ZonexGreenAccent)
                        MetricRow(icon = Icons.Default.DirectionsRun, value = "7.842", label = "PASSOS", tint = ZonexGold)
                        MetricRow(icon = Icons.Default.LocalFireDepartment, value = "542", label = "KCAL", tint = Color(0xFFA855F7))
                    }

                    // Right Metrics Card (DISTÂNCIA, TEMPO, VELOCIDADE)
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xD90C0C14))
                            .border(1.dp, Color(0xFF2E1016), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MetricRow(icon = Icons.Default.DirectionsRun, value = "5,23 km", label = "DISTÂNCIA", tint = Color.White)
                        MetricRow(icon = Icons.Default.Schedule, value = "00:42:15", label = "TEMPO", tint = Color.White)
                        MetricRow(icon = Icons.Default.Speed, value = "7,4 km/h", label = "VELOCIDADE", tint = Color.White)
                    }
                }
            }

            // Bottom Action Controls Area: BOOSTS | INICIAR CORRIDA | LOJA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // BOOSTS Hex Button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialogInfo = "BOOSTS ATIVOS:\n⚡ 2x XP nas próximas 2 corridas\n🛡️ Proteção de Célula ativa por 24h"
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(HexagonShape)
                                .background(Color(0xFF14141C))
                                .border(1.5.dp, Color(0xFF381418), HexagonShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Boosts",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "BOOSTS",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // LOJA Hex Button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialogInfo = "LOJA ZONEX:\n💎 Pacote 500 Gemas - 1000 ZX\n👟 Tênis Cyber Turbo - 2500 ZX\n🛡️ Escudo de Território - 150 Gemas"
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(HexagonShape)
                                .background(Color(0xFF14141C))
                                .border(1.5.dp, Color(0xFF381418), HexagonShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Loja",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                            // Red notification dot
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 4.dp)
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(ZonexRedPrimary)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "LOJA",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Huge Center Button: INICIAR CORRIDA
                Box(
                    modifier = Modifier
                        .size(108.dp)
                        .clickable(onClick = {
                            ZonexRepository.startRunSession()
                            onNavigateToRun()
                        })
                        .testTag("main_map_btn_iniciar_corrida"),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer Pulsating Neon Ring
                    Box(
                        modifier = Modifier
                            .size(108.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0x66FF1E27), CircleShape)
                    )
                    // Mid Ring
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .border(2.dp, ZonexRedPrimary, CircleShape)
                    )
                    // Core Button Fill
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        ZonexRedPrimary,
                                        ZonexRedCrimson,
                                        Color(0xFF7A040B)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsRun,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "INICIAR",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "CORRIDA",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            // Bottom Navigation
            ZonexBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    when (tab) {
                        ZonexNavTab.MAPA -> {}
                        ZonexNavTab.PERFIL -> onNavigateToProfile()
                        ZonexNavTab.MISSOES -> {
                            showDialogInfo = "Painel de Missões:\n• Domine 3 células em Zonex City\n• Acumule 10km semanais\n• Complete uma corrida na Zona Segura"
                        }
                        ZonexNavTab.RANKING -> {
                            showDialogInfo = "🏆 RANKING REGIONAL:\n#1 CORREDOR_ZX (Você) - 36 Territórios\n#2 CyberBlaze - 34 Territórios\n#3 ValkyrieRun - 29 Territórios"
                        }
                        ZonexNavTab.LOJA -> {
                            showDialogInfo = "🛍️ Loja ZONEX:\nAcesse a aba Avatar para personalizar roupas exclusivas!"
                        }
                    }
                }
            )
        }

        // Info Dialog
        if (showDialogInfo != null) {
            AlertDialog(
                onDismissRequest = { showDialogInfo = null },
                title = {
                    Text(
                        text = "ZONEX INTEL",
                        color = ZonexRedPrimary,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                },
                text = {
                    Text(
                        text = showDialogInfo!!,
                        color = ZonexGreyLight,
                        fontSize = 14.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = { showDialogInfo = null }) {
                        Text("ENTENDIDO", color = ZonexRedPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                containerColor = ZonexCardSurface,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
private fun QuickActionBadge(
    title: String,
    badgeCount: Int?,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xE60F0F16))
            .border(1.dp, Color(0xFF2C1014), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            if (badgeCount != null) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(ZonexRedPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$badgeCount",
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            color = ZonexGreyLight,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MapFloatIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    desc: String,
    isRed: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xE60F0F16))
            .border(1.dp, if (isRed) ZonexRedPrimary else Color(0xFF2C1014), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = desc,
            tint = if (isRed) ZonexRedPrimary else ZonexGreyLight,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun MetricRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    tint: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = value,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                color = ZonexGreyMuted,
                fontSize = 8.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
