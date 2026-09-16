package com.example.ui.components

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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.UserProfile
import com.example.ui.theme.ZonexBorderDark
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexCardSurfaceElevated
import com.example.ui.theme.ZonexGemRed
import com.example.ui.theme.ZonexGold
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedCrimson
import com.example.ui.theme.ZonexRedDark
import com.example.ui.theme.ZonexRedGlow
import com.example.ui.theme.ZonexRedPrimary
import com.example.ui.theme.ZonexRedSubtleBorder

@Composable
fun ZonexLogo(
    modifier: Modifier = Modifier,
    showSlogan: Boolean = true,
    scale: Float = 1f
) {
    Column(
        modifier = modifier.scale(scale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Futuristic Stylized X with Metallic "ZONEX"
        Box(
            modifier = Modifier.size(160.dp, 100.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(140.dp, 90.dp)) {
                val w = size.width
                val h = size.height
                val path1 = Path().apply {
                    moveTo(w * 0.15f, 0f)
                    lineTo(w * 0.38f, 0f)
                    lineTo(w * 0.85f, h)
                    lineTo(w * 0.62f, h)
                    close()
                }
                val path2 = Path().apply {
                    moveTo(w * 0.85f, 0f)
                    lineTo(w * 0.62f, 0f)
                    lineTo(w * 0.15f, h)
                    lineTo(w * 0.38f, h)
                    close()
                }

                // Glowing outer red borders
                drawPath(path1, color = ZonexRedPrimary, style = Stroke(width = 8f, cap = StrokeCap.Round))
                drawPath(path2, color = ZonexRedPrimary, style = Stroke(width = 8f, cap = StrokeCap.Round))

                // Inner dark fill
                drawPath(path1, color = Color(0x99180406))
                drawPath(path2, color = Color(0x99180406))

                // Fine cybernetic line
                drawPath(path1, color = Color(0xFFFF4D55), style = Stroke(width = 2.5f))
                drawPath(path2, color = Color(0xFFFF4D55), style = Stroke(width = 2.5f))
            }

            // Bold Metallic Typography overlay
            Text(
                text = "ZONEX",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp,
                style = MaterialTheme.typography.displayMedium
            )
        }

        if (showSlogan) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "CORRA.",
                    color = ZonexGreyLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "CONQUISTE.",
                    color = ZonexRedPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "DOMINE.",
                    color = ZonexGreyLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
fun ZonexButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    testTag: String = "zonex_button"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shape = chamferedShape(cutSize = 8.dp)

    val scale = if (isPressed) 0.97f else 1.0f

    val backgroundBrush = if (isPrimary) {
        Brush.verticalGradient(
            colors = listOf(
                ZonexRedPrimary,
                ZonexRedCrimson,
                ZonexRedDark
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF16080B),
                Color(0xFF0D0406)
            )
        )
    }

    val borderStroke = if (isPrimary) {
        BorderStroke(1.5.dp, Color(0xFFFF6670))
    } else {
        BorderStroke(1.5.dp, ZonexRedPrimary)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .scale(scale)
            .testTag(testTag)
            .clip(shape)
            .border(borderStroke, shape)
            .background(backgroundBrush)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text.uppercase(),
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.8.sp
            )
        }
    }
}

@Composable
fun ZonexTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isValid: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    testTag: String = "zonex_input"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            placeholder = {
                Text(
                    text = placeholder,
                    color = ZonexGreyMuted,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (value.isNotEmpty()) ZonexRedPrimary else ZonexGreyMuted,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                            tint = ZonexRedPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else if (isValid && value.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFF10B981), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✓", color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = ZonexCardSurface,
                unfocusedContainerColor = Color(0xFF0C0C12),
                focusedBorderColor = ZonexRedPrimary,
                unfocusedBorderColor = Color(0xFF2C1014),
                cursorColor = ZonexRedPrimary,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

        if (!errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = ZonexRedPrimary,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun ZonexTopHeader(
    profile: UserProfile,
    onAvatarClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // User Profile & Level
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onAvatarClick)
        ) {
            Box(
                modifier = Modifier.size(46.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer glowing circle
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(2.dp, ZonexRedPrimary, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar_runner),
                        contentDescription = "Avatar do Corredor",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(44.dp)
                    )
                }

                // Small Hexagonal Level badge on bottom right
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .clip(HexagonShape)
                        .background(ZonexPureBlack)
                        .border(1.dp, ZonexRedPrimary, HexagonShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${profile.level}",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = profile.username,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(12.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "NÍVEL ",
                        color = ZonexGreyMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${profile.level}",
                        color = ZonexRedPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${profile.currentXp} / ${profile.maxXp} XP",
                        color = ZonexGreyText,
                        fontSize = 10.sp
                    )
                }

                // XP bar
                Spacer(modifier = Modifier.height(3.dp))
                LinearProgressIndicator(
                    progress = { (profile.currentXp.toFloat() / profile.maxXp.toFloat()).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .width(110.dp)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = ZonexRedPrimary,
                    trackColor = Color(0xFF261014)
                )
            }
        }

        // Currencies (ZX coins & Gems) & Bell
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // ZX Coin Badge
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
                    text = "${profile.zxCoins}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Comprar ZX",
                    tint = ZonexGold,
                    modifier = Modifier.size(12.dp)
                )
            }

            // Gems Badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF1E0A0E))
                    .border(1.dp, Color(0xFF631520), RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Diamond,
                    contentDescription = null,
                    tint = ZonexGemRed,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${profile.gems}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Comprar Gemas",
                    tint = ZonexRedPrimary,
                    modifier = Modifier.size(12.dp)
                )
            }

            // Notification Bell
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF14141C))
                    .clickable(onClick = onNotificationsClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = ZonexGreyLight,
                    modifier = Modifier.size(18.dp)
                )
                // Red badge dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(ZonexRedPrimary)
                )
            }
        }
    }
}

enum class ZonexNavTab {
    MAPA,
    MISSOES,
    RANKING,
    LOJA,
    PERFIL
}

@Composable
fun ZonexBottomNav(
    selectedTab: ZonexNavTab,
    onTabSelected: (ZonexNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color(0xFF09090D),
        border = BorderStroke(1.dp, Color(0xFF1E1418))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tabs = listOf(
                Triple(ZonexNavTab.MAPA, "MAPA", Icons.Default.Map),
                Triple(ZonexNavTab.MISSOES, "MISSÕES", Icons.Default.TrackChanges),
                Triple(ZonexNavTab.RANKING, "RANKING", Icons.Default.EmojiEvents),
                Triple(ZonexNavTab.LOJA, "LOJA", Icons.Default.ShoppingCart),
                Triple(ZonexNavTab.PERFIL, "PERFIL", Icons.Default.Person)
            )

            tabs.forEach { (tab, label, icon) ->
                val isSelected = selectedTab == tab
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(tab) }
                        )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isSelected) {
                            // Soft red glow underneath active icon
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33FF1E27))
                            )
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (isSelected) ZonexRedPrimary else ZonexGreyMuted,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = label,
                        color = if (isSelected) ZonexRedPrimary else ZonexGreyMuted,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ZonexCard(
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFF2B1015),
    backgroundColor: Color = ZonexCardSurface,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {
        content()
    }
}
