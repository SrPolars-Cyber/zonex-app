package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ZonexRepository
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexBottomNav
import com.example.ui.components.ZonexButton
import com.example.ui.components.ZonexCard
import com.example.ui.components.ZonexNavTab
import com.example.ui.theme.ZonexBorderDark
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexCardSurfaceElevated
import com.example.ui.theme.ZonexGemRed
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreenAccent
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedPrimary
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onNavigateToAvatar: () -> Unit,
    onNavigateToMap: () -> Unit
) {
    val userProfile by ZonexRepository.userProfile.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showEditBioDialog by remember { mutableStateOf(false) }
    var tempBio by remember { mutableStateOf(userProfile.bio) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZonexPureBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
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
                            .clickable(onClick = onBack),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = ZonexRedPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "PERFIL",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Link do perfil copiado para compartilhamento!")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartilhar",
                            tint = ZonexGreyLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Configurações da conta: ZONEX v1.0.0")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
                            tint = ZonexGreyLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Avatar with Halo & Level Badge
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer glow
                    Box(
                        modifier = Modifier
                            .size(108.dp)
                            .clip(CircleShape)
                            .border(2.5.dp, ZonexRedPrimary, CircleShape)
                            .background(Color(0x33FF1E27))
                    )
                    // Image
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar_runner),
                        contentDescription = "Avatar do Corredor",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onNavigateToAvatar)
                    )
                    // Edit badge overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(ZonexRedPrimary)
                            .border(1.5.dp, Color.White, CircleShape)
                            .clickable(onClick = onNavigateToAvatar)
                            .testTag("profile_btn_edit_avatar"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // User name
                Text(
                    text = userProfile.username,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Badges row: Conquistador, Clã, ID
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Title chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF241C0A))
                            .border(1.dp, Color(0xFF6B4706), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🏆 ", fontSize = 10.sp)
                        Text(
                            text = userProfile.title,
                            color = ZonexGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Clan chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF260A0E))
                            .border(1.dp, Color(0xFF631520), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = ZonexRedPrimary,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = userProfile.clan,
                            color = ZonexRedPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // ID Chip with Copy
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF14141E))
                            .border(1.dp, Color(0xFF2C2C3E), RoundedCornerShape(12.dp))
                            .clickable {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("ID copiado: ${userProfile.runnerId}")
                                }
                            }
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ID: ${userProfile.runnerId}",
                            color = ZonexGreyText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar ID",
                            tint = ZonexGreyMuted,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Level Progress Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF101018))
                        .border(1.dp, Color(0xFF261014), RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(HexagonShape)
                                .background(ZonexRedPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${userProfile.level}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "NÍVEL ${userProfile.level}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${userProfile.currentXp} / ${userProfile.maxXp} XP",
                                color = ZonexGreyMuted,
                                fontSize = 10.sp
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { (userProfile.currentXp.toFloat() / userProfile.maxXp.toFloat()).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .width(140.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = ZonexRedPrimary,
                        trackColor = Color(0xFF2B1015)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 4 Currency / Stats Columns
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F0F16))
                        .border(1.dp, Color(0xFF2A1016), RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatMetricColumn("3.250", "ZX", ZonexGold)
                    StatMetricColumn("150", "Gemas", ZonexGemRed)
                    StatMetricColumn("${userProfile.territoriesCount}", "Territórios", ZonexRedPrimary)
                    StatMetricColumn("${userProfile.achievementsCount}", "Conquistas", Color(0xFF38BDF8))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bio Card
                ZonexCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = Color(0xFF281015)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "“${userProfile.bio}”",
                            color = ZonexGreyLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                tempBio = userProfile.bio
                                showEditBioDialog = true
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar Frase",
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // RESUMO DE ATIVIDADES
                ZonexCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsRun,
                                contentDescription = null,
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "RESUMO DE ATIVIDADES",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ActivityItem(
                                icon = Icons.Default.DirectionsRun,
                                label = "DISTÂNCIA TOTAL",
                                value = "1.248 KM",
                                tint = Color.White
                            )
                            ActivityItem(
                                icon = Icons.Default.Schedule,
                                label = "TEMPO TOTAL",
                                value = "108:45:32",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ActivityItem(
                                icon = Icons.Default.LocalFireDepartment,
                                label = "CALORIAS",
                                value = "85.742 KCAL",
                                tint = Color.White
                            )
                            ActivityItem(
                                icon = Icons.Default.DirectionsRun,
                                label = "PASSOS",
                                value = "1.582.462",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // TERRITÓRIOS CONQUISTADOS (Hexagon Canvas & Breakdown)
                ZonexCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TERRITÓRIOS CONQUISTADOS",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left Hexagonal Territory Mesh Visual
                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF0B0B10))
                                    .border(1.dp, Color(0xFF261014), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(90.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    val r = 12f

                                    // Draw concentric hexagonal cells representing conquered clusters
                                    for (dx in -1..1) {
                                        for (dy in -2..2) {
                                            val cx = w / 2f + dx * 22f + (dy % 2) * 11f
                                            val cy = h / 2f + dy * 19f
                                            val isCenter = dx == 0 && dy == 0
                                            val hex = Path().apply {
                                                for (i in 0..5) {
                                                    val angle = Math.PI / 3 * i
                                                    val px = (cx + r * cos(angle)).toFloat()
                                                    val py = (cy + r * sin(angle)).toFloat()
                                                    if (i == 0) moveTo(px, py) else lineTo(px, py)
                                                }
                                                close()
                                            }
                                            drawPath(
                                                hex,
                                                color = if (isCenter) ZonexRedPrimary else Color(0x66FF1E27),
                                                style = Fill
                                            )
                                            drawPath(
                                                hex,
                                                color = Color(0xFFFF6670),
                                                style = Stroke(width = 1f)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            // District List Breakdown
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                userProfile.districtBreakdown.forEach { district ->
                                    DistrictProgressRow(
                                        name = district.name,
                                        cells = district.cells,
                                        color = Color(district.colorHex)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // CONQUISTAS RECENTES (Horizontal Scroll of Hexagons)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = ZonexGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CONQUISTAS RECENTES",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = "VER TODAS",
                            color = ZonexRedPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Total de 152 conquistas desbloqueadas!")
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(ZonexRepository.achievements) { ach ->
                            AchievementCard(ach)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Button to customize Avatar
                ZonexButton(
                    text = "PERSONALIZAR AVATAR",
                    onClick = onNavigateToAvatar,
                    isPrimary = true,
                    leadingIcon = Icons.Default.Edit,
                    testTag = "profile_btn_customize_avatar"
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom Navigation
            ZonexBottomNav(
                selectedTab = ZonexNavTab.PERFIL,
                onTabSelected = { tab ->
                    if (tab == ZonexNavTab.MAPA) onNavigateToMap()
                }
            )
        }

        // Edit Bio Dialog
        if (showEditBioDialog) {
            AlertDialog(
                onDismissRequest = { showEditBioDialog = false },
                title = {
                    Text(
                        text = "EDITAR FRASE DO PERFIL",
                        color = ZonexRedPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                },
                text = {
                    OutlinedTextField(
                        value = tempBio,
                        onValueChange = { tempBio = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ZonexRedPrimary,
                            unfocusedBorderColor = Color(0xFF331418)
                        )
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        ZonexRepository.updateBio(tempBio)
                        showEditBioDialog = false
                    }) {
                        Text("SALVAR", color = ZonexRedPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditBioDialog = false }) {
                        Text("CANCELAR", color = ZonexGreyMuted)
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
private fun StatMetricColumn(
    value: String,
    label: String,
    accentColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = accentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = ZonexGreyText,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ActivityItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    tint: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ZonexRedPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = label,
                color = ZonexGreyMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DistrictProgressRow(
    name: String,
    cells: Int,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$cells células",
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        LinearProgressIndicator(
            progress = { (cells / 15f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = color,
            trackColor = Color(0xFF1E1418)
        )
    }
}

@Composable
private fun AchievementCard(
    achievement: com.example.model.Achievement
) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF0F0F16))
            .border(1.dp, Color(0xFF2C1014), RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(HexagonShape)
                    .background(Color(0xFF1E080C))
                    .border(1.dp, ZonexRedPrimary, HexagonShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (achievement.iconType) {
                        "flag" -> Icons.Default.Flag
                        "shoe" -> Icons.Default.DirectionsRun
                        "flame" -> Icons.Default.LocalFireDepartment
                        else -> Icons.Default.Shield
                    },
                    contentDescription = null,
                    tint = ZonexRedPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = achievement.title,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${achievement.current}/${achievement.target}",
                color = ZonexGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
