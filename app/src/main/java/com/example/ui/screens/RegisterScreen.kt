package com.example.ui.screens

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ZonexRepository
import com.example.ui.components.HexagonShape
import com.example.ui.components.ZonexButton
import com.example.ui.components.ZonexLogo
import com.example.ui.components.ZonexTextField
import com.example.ui.theme.ZonexCardSurface
import com.example.ui.theme.ZonexGreyLight
import com.example.ui.theme.ZonexGreyMuted
import com.example.ui.theme.ZonexGreyText
import com.example.ui.theme.ZonexPureBlack
import com.example.ui.theme.ZonexRedPrimary
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBack: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var selectedDay by remember { mutableStateOf("15") }
    var selectedMonth by remember { mutableStateOf("09") }
    var selectedYear by remember { mutableStateOf("2000") }

    var selectedGender by remember { mutableStateOf("Masculino") }
    var receiveNews by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

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
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, ZonexRedPrimary, RoundedCornerShape(8.dp))
                        .background(Color(0xFF14080B))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = ZonexRedPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Logo & Titles
            ZonexLogo(showSlogan = false, scale = 0.85f)

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "CADASTRE-SE",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Crie sua conta e comece a conquistar.",
                color = ZonexGreyMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Red accent line
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(ZonexRedPrimary)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form Inputs
            ZonexTextField(
                value = fullName,
                onValueChange = { fullName = it; errorMessage = null },
                placeholder = "Nome completo",
                leadingIcon = Icons.Default.Person,
                testTag = "register_input_fullname"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ZonexTextField(
                value = username,
                onValueChange = { username = it; errorMessage = null },
                placeholder = "Nome de usuário",
                leadingIcon = Icons.Default.Person,
                isValid = username.length >= 3,
                testTag = "register_input_username"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ZonexTextField(
                value = email,
                onValueChange = { email = it; errorMessage = null },
                placeholder = "E-mail",
                leadingIcon = Icons.Default.Email,
                isValid = email.contains("@") && email.contains("."),
                testTag = "register_input_email"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ZonexTextField(
                value = password,
                onValueChange = { password = it; errorMessage = null },
                placeholder = "Senha",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                testTag = "register_input_password"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ZonexTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; errorMessage = null },
                placeholder = "Confirmar senha",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                testTag = "register_input_confirm_password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DATA DE NASCIMENTO
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "DATA DE NASCIMENTO",
                    color = ZonexGreyMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BirthPickerBox(label = "Dia", value = selectedDay, modifier = Modifier.weight(1f)) {
                        selectedDay = it
                    }
                    BirthPickerBox(label = "Mês", value = selectedMonth, modifier = Modifier.weight(1f)) {
                        selectedMonth = it
                    }
                    BirthPickerBox(label = "Ano", value = selectedYear, modifier = Modifier.weight(1.2f)) {
                        selectedYear = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // GÊNERO
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "GÊNERO",
                    color = ZonexGreyMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val genders = listOf(
                        Triple("Masculino", Icons.Default.Male, "register_gender_male"),
                        Triple("Feminino", Icons.Default.Female, "register_gender_female"),
                        Triple("Outro", Icons.Default.MoreHoriz, "register_gender_other")
                    )

                    genders.forEach { (gender, icon, tag) ->
                        val isSelected = selectedGender == gender
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) ZonexRedPrimary else Color(0xFF2B1015),
                                    RoundedCornerShape(8.dp)
                                )
                                .background(if (isSelected) Color(0xFF1E080C) else Color(0xFF0C0C12))
                                .clickable { selectedGender = gender }
                                .testTag(tag),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (isSelected) ZonexRedPrimary else ZonexGreyMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = gender,
                                    color = if (isSelected) ZonexRedPrimary else ZonexGreyLight,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkboxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = receiveNews,
                    onCheckedChange = { receiveNews = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ZonexRedPrimary,
                        uncheckedColor = ZonexGreyMuted,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "Quero receber novidades e promoções do ZONEX.",
                    color = ZonexGreyText,
                    fontSize = 12.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreeTerms,
                    onCheckedChange = {
                        agreeTerms = it
                        errorMessage = null
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ZonexRedPrimary,
                        uncheckedColor = ZonexGreyMuted,
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier.testTag("register_checkbox_terms")
                )
                Column {
                    Text(
                        text = "Li e concordo com os Termos de Uso e Política de Privacidade.",
                        color = ZonexGreyText,
                        fontSize = 12.sp
                    )
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage!!,
                    color = ZonexRedPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CRIAR CONTA Button
            ZonexButton(
                text = "CRIAR CONTA",
                onClick = {
                    when {
                        fullName.isBlank() || username.isBlank() || email.isBlank() || password.isBlank() -> {
                            errorMessage = "Preencha todos os campos obrigatórios."
                        }
                        !email.contains("@") || !email.contains(".") -> {
                            errorMessage = "Insira um e-mail válido."
                        }
                        password != confirmPassword -> {
                            errorMessage = "As senhas não coincidem."
                        }
                        !agreeTerms -> {
                            errorMessage = "Você deve concordar com os Termos de Uso."
                        }
                        else -> {
                            ZonexRepository.registerNewUser(fullName, username, email)
                            onRegisterSuccess()
                        }
                    }
                },
                isPrimary = true,
                testTag = "register_btn_submit"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // "ou" Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF381216),
                    thickness = 1.dp
                )
                Text(
                    text = "ou",
                    color = ZonexRedPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF381216),
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Social Signup
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .clickable {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Cadastro com Google estará disponível na próxima atualização.")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "G",
                        color = Color(0xFF4285F4),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Cadastrar com Google",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F0F14))
                    .border(1.dp, Color(0xFF331418), RoundedCornerShape(8.dp))
                    .clickable {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Cadastro com Apple estará disponível na próxima atualização.")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Cadastrar com Apple",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Já tem uma conta? ",
                    color = ZonexGreyMuted,
                    fontSize = 13.sp
                )
                Text(
                    text = "Faça login",
                    color = ZonexRedPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(onClick = onNavigateToLogin)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
        )
    }
}

@Composable
private fun BirthPickerBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF2C1014), RoundedCornerShape(8.dp))
            .background(Color(0xFF0C0C12))
            .clickable { expanded = true }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value.isNotEmpty()) value else label,
                color = if (value.isNotEmpty()) Color.White else ZonexGreyMuted,
                fontSize = 13.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = ZonexRedPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(ZonexCardSurface)
        ) {
            when (label) {
                "Dia" -> (1..31).map { String.format("%02d", it) }.take(10).forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.White) },
                        onClick = {
                            onValueSelected(item)
                            expanded = false
                        }
                    )
                }
                "Mês" -> (1..12).map { String.format("%02d", it) }.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.White) },
                        onClick = {
                            onValueSelected(item)
                            expanded = false
                        }
                    )
                }
                "Ano" -> (1975..2008).reversed().take(10).map { it.toString() }.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.White) },
                        onClick = {
                            onValueSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
