package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ZonexRepository
import com.example.model.AvatarClothingItem
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexButton
import com.example.ui.components.ZonexCard
import com.example.ui.theme.ZonexBorderDark
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexCardSurfaceElevated
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreenAccent
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedPrimary
import kotlinx.coroutines.launch

@Composable
fun AvatarScreen(
    onBack: () -> Unit
) {
    val userProfile by ZonexRepository.userProfile.collectAsState()
    var selectedShirtId by remember { mutableStateOf(userProfile.selectedShirtId) }
    var selectedSubTab by remember { mutableStateOf("CAMISETAS") }
    var selectedCategory by remember { mutableStateOf("Roupas") }

    var rotationAngle by remember { mutableFloatStateOf(0f) }
    var isZoomed by remember { mutableStateOf(false) }

    val animatedRotation by animateFloatAsState(targetValue = rotationAngle, label = "rotate")
    val animatedScale by animateFloatAsState(targetValue = if (isZoomed) 1.25f else 1.0f, label = "zoom")

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZonexPureBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
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

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "AVATAR",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Personalize seu estilo na ZONEX.",
                            color = ZonexGreyMuted,
                            fontSize = 10.sp
                        )
                    }
                }

                // ZX Currency Chip
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF17130A))
                        .border(1.dp, Color(0xFF6B4706), RoundedCornerShape(14.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(HexagonShape)
                            .background(ZonexGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ZX",
                            color = Color.Black,
                            fontSize = 7.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${userProfile.zxCoins}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Main 3-Column Studio Showcase
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                // 1. Left Vertical Category Rail
                Column(
                    modifier = Modifier
                        .width(72.dp)
                        .fillMaxHeight()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val categories = listOf("Roupas", "Acessórios", "Tênis", "Mochilas", "Emotes", "Títulos")
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) ZonexRedPrimary else Color(0xFF231418),
                                    RoundedCornerShape(6.dp)
                                )
                                .background(if (isSelected) Color(0xFF260A0E) else Color(0xFF0D0D14))
                                .clickable { selectedCategory = cat },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else ZonexGreyMuted,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                // 2. Center Stage: Full Body Runner on Sci-Fi Pedestal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    // Hologram backdrop glow
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0x33FF1E27), Color.Transparent),
                                    radius = 320f
                                )
                            )
                    )

                    // Runner Character Image
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar_full),
                        contentDescription = "Corredor ZONEX",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(animatedScale)
                            .rotate(animatedRotation)
                    )

                    // GIRAR Button (Left)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 4.dp, bottom = 12.dp)
                            .size(44.dp)
                            .clip(HexagonShape)
                            .background(Color(0xE60F0F16))
                            .border(1.dp, Color(0xFF331418), HexagonShape)
                            .clickable {
                                rotationAngle = if (rotationAngle == 0f) 180f else 0f
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Girar",
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text("GIRAR", color = Color.White, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // ZOOM Button (Right)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 4.dp, bottom = 12.dp)
                            .size(44.dp)
                            .clip(HexagonShape)
                            .background(Color(0xE60F0F16))
                            .border(1.dp, Color(0xFF331418), HexagonShape)
                            .clickable { isZoomed = !isZoomed },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (isZoomed) Icons.Default.ZoomOut else Icons.Default.ZoomIn,
                                contentDescription = "Zoom",
                                tint = ZonexRedPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(if (isZoomed) "REDUZIR" else "ZOOM", color = Color.White, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 3. Right Stats Column: Level, Attributes & Current Title
                Column(
                    modifier = Modifier
                        .width(96.dp)
                        .fillMaxHeight()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Level box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F0F16))
                            .border(1.dp, Color(0xFF281014), RoundedCornerShape(8.dp))
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("NÍVEL 12", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(2.dp))
                        LinearProgressIndicator(
                            progress = { 0.61f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = ZonexRedPrimary,
                            trackColor = Color(0xFF261014)
                        )
                    }

                    // ATRIBUTOS
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F0F16))
                            .border(1.dp, Color(0xFF281014), RoundedCornerShape(8.dp))
                            .padding(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "ATRIBUTOS",
                            color = ZonexGreyMuted,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )

                        AttrMiniRow(Icons.Default.Favorite, "Saúde", "${userProfile.healthAttr}", ZonexGreenAccent)
                        AttrMiniRow(Icons.Default.DirectionsRun, "Velocidade", "${userProfile.speedAttr}", ZonexRedPrimary)
                        AttrMiniRow(Icons.Default.Shield, "Resistência", "${userProfile.enduranceAttr}", Color(0xFF38BDF8))
                        AttrMiniRow(Icons.Default.TrackChanges, "Precisão", "${userProfile.precisionAttr}", ZonexGold)
                        AttrMiniRow(Icons.Default.FitnessCenter, "Força", "${userProfile.strengthAttr}", Color(0xFFA855F7))
                    }

                    // TÍTULO ATUAL
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF161208))
                            .border(1.dp, Color(0xFF4A3408), RoundedCornerShape(8.dp))
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("TÍTULO ATUAL", color = ZonexGreyMuted, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(userProfile.title, color = ZonexGold, fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            // Bottom Clothing Selector Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color(0xFF0D0D14))
                    .border(1.dp, Color(0xFF261014), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                // Subtabs: CAMISETAS | CALÇAS | JAQUETAS | CONJUNTOS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val subTabs = listOf("CAMISETAS", "CALÇAS", "JAQUETAS", "CONJUNTOS")
                    subTabs.forEach { tab ->
                        val isSelected = selectedSubTab == tab
                        Text(
                            text = tab,
                            color = if (isSelected) ZonexRedPrimary else ZonexGreyMuted,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier
                                .clickable { selectedSubTab = tab }
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Clothing Carousel
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(ZonexRepository.availableShirts) { shirt ->
                        val isSelected = selectedShirtId == shirt.id
                        ShirtSelectorCard(
                            shirt = shirt,
                            isSelected = isSelected,
                            onSelect = {
                                if (!shirt.isLocked) {
                                    selectedShirtId = shirt.id
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Item bloqueado. Requer Nível ${shirt.levelReq}!")
                                    }
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // SALVAR ALTERAÇÕES Button
                ZonexButton(
                    text = "SALVAR ALTERAÇÕES",
                    onClick = {
                        ZonexRepository.selectShirt(selectedShirtId)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Personalização salva com sucesso!")
                        }
                    },
                    isPrimary = true,
                    testTag = "avatar_btn_save"
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
        )
    }
}

@Composable
private fun AttrMiniRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    name: String,
    value: String,
    tint: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(name, color = ZonexGreyLight, fontSize = 8.sp)
        }
        Text(value, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ShirtSelectorCard(
    shirt: AvatarClothingItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(88.dp)
            .height(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.5.dp,
                if (isSelected) ZonexRedPrimary else if (shirt.isLocked) Color(0xFF201416) else Color(0xFF331A20),
                RoundedCornerShape(8.dp)
            )
            .background(if (isSelected) Color(0xFF260A0E) else Color(0xFF12121A))
            .clickable(onClick = onSelect)
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            // Visual Mini Swatch / Hexagon representing clothing
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(HexagonShape)
                    .background(Color(shirt.primaryColorHex))
                    .border(1.5.dp, Color(shirt.accentColorHex), HexagonShape),
                contentAlignment = Alignment.Center
            ) {
                if (shirt.isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Bloqueado",
                        tint = ZonexGreyMuted,
                        modifier = Modifier.size(16.dp)
                    )
                } else if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selecionado",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = "ZX",
                        color = Color(shirt.accentColorHex),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Text(
                text = if (shirt.isLocked) "Nív. ${shirt.levelReq}" else shirt.name.take(12),
                color = if (shirt.isLocked) ZonexGreyMuted else Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
