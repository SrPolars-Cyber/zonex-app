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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexButton
import com.example.ui.components.ZonexLogo
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedPrimary

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZonexPureBlack)
    ) {
        // Atmospheric Background: runner silhouette on red cyber grid skyline
        Image(
            painter = painterResource(id = R.drawable.img_hero_runner),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient vignettes for high contrast readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC060608),
                            Color(0x40060608),
                            Color(0xE6060608),
                            Color(0xFF060608)
                        )
                    )
                )
        )

        // Screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Section: Stylized Logo & Slogan
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                ZonexLogo(showSlogan = true)
            }

            // Mid Section: Cyber Beacons decoration overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Hexagon Beacon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .alpha(pulseAlpha)
                        .clip(HexagonShape)
                        .background(Color(0x66180406))
                        .border(1.5.dp, ZonexRedPrimary, HexagonShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = null,
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Center Crown / Trophy Beacon
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .alpha(pulseAlpha)
                        .clip(HexagonShape)
                        .background(Color(0x6620060A))
                        .border(2.dp, ZonexRedPrimary, HexagonShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Right Hexagon Beacon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .alpha(pulseAlpha)
                        .clip(HexagonShape)
                        .background(Color(0x66180406))
                        .border(1.5.dp, ZonexRedPrimary, HexagonShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = null,
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ZonexButton(
                    text = "ENTRAR",
                    onClick = onNavigateToLogin,
                    isPrimary = true,
                    testTag = "welcome_btn_entrar"
                )

                ZonexButton(
                    text = "CRIAR CONTA",
                    onClick = onNavigateToRegister,
                    isPrimary = false,
                    testTag = "welcome_btn_criar_conta"
                )
            }
        }
    }
}
