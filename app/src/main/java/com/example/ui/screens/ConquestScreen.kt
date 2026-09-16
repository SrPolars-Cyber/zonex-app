package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.model.TerritoryConquestReward
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexButton
import com.example.ui.components.ZonexCard
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedPrimary

@Composable
fun ConquestScreen(
    onContinue: () -> Unit,
    onViewTerritory: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
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

        // Heavy dark overlay with crimson tint
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xF2060608),
                            Color(0xEB180408),
                            Color(0xF5060608)
                        )
                    )
                )
        )

        // Celebration Dialog Card Centered
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Close / Skip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    ZonexRepository.applyConquestReward()
                    onContinue()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fechar",
                        tint = ZonexGreyMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Central Conquest Trophy Card
            ZonexCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = ZonexRedPrimary,
                backgroundColor = Color(0xF20D0D14)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Glowing Red Hexagon with "X"
                    Box(
                        modifier = Modifier.size(88.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Ambient glow halo
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(Color(0x44FF1E27))
                        )
                        // Hexagon Icon Box
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(HexagonShape)
                                .background(Color(0xFF20060A))
                                .border(2.dp, ZonexRedPrimary, HexagonShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "X",
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title
                    Text(
                        text = "TERRITÓRIO CONQUISTADO!",
                        color = ZonexRedPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Captured Cells Badge
                    Text(
                        text = "+ 6 CÉLULAS",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Você expandiu o domínio da ZONEX CITY.\nContinue se movendo. Continue conquistando.",
                        color = ZonexGreyText,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // RECOMPENSAS Card
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF14141E))
                            .border(1.dp, Color(0xFF281418), RoundedCornerShape(10.dp))
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "RECOMPENSAS",
                            color = ZonexGreyMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        RewardRow(
                            iconText = "ZX",
                            label = "+ 250 ZX",
                            sub = "Moeda do jogo",
                            color = ZonexGold
                        )

                        RewardRow(
                            iconVector = Icons.Default.Star,
                            label = "+ 150 XP",
                            sub = "Experiência de corredor",
                            color = ZonexRedPrimary
                        )

                        RewardRow(
                            iconVector = Icons.Default.MilitaryTech,
                            label = "+ 10 PONTOS DE DOMÍNIO",
                            sub = "Poder territorial de clã",
                            color = Color(0xFF38BDF8)
                        )
                    }
                }
            }

            // Bottom Buttons: VER MEU TERRITÓRIO & CONTINUAR
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ZonexButton(
                    text = "VER MEU TERRITÓRIO",
                    onClick = {
                        ZonexRepository.applyConquestReward()
                        onViewTerritory()
                    },
                    isPrimary = false,
                    leadingIcon = Icons.Default.Flag,
                    testTag = "conquest_btn_ver_territorio"
                )

                ZonexButton(
                    text = "CONTINUAR",
                    onClick = {
                        ZonexRepository.applyConquestReward()
                        onContinue()
                    },
                    isPrimary = true,
                    testTag = "conquest_btn_continuar"
                )
            }
        }
    }
}

@Composable
private fun RewardRow(
    iconText: String? = null,
    iconVector: androidx.compose.ui.graphics.vector.ImageVector? = null,
    label: String,
    sub: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(HexagonShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            if (iconText != null) {
                Text(
                    text = iconText,
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )
            } else if (iconVector != null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = sub,
                color = ZonexGreyMuted,
                fontSize = 10.sp
            )
        }
    }
}
