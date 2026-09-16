package com.example.data

import com.example.model.Achievement
import com.example.model.AvatarClothingItem
import com.example.model.LiveRunStats
import com.example.model.TerritoryConquestReward
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

object ZonexRepository {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _runStats = MutableStateFlow(LiveRunStats())
    val runStats: StateFlow<LiveRunStats> = _runStats.asStateFlow()

    val availableShirts: List<AvatarClothingItem> = listOf(
        AvatarClothingItem(
            id = "shirt_zonex_default",
            category = "CAMISETAS",
            name = "ZONEX Tech Classic",
            primaryColorHex = 0xFF14141A,
            accentColorHex = 0xFFFF1E27,
            isLocked = false,
            levelReq = 1
        ),
        AvatarClothingItem(
            id = "shirt_cyber_red",
            category = "CAMISETAS",
            name = "Cyber Red Circuit",
            primaryColorHex = 0xFF2A090D,
            accentColorHex = 0xFFFF3344,
            isLocked = false,
            levelReq = 5
        ),
        AvatarClothingItem(
            id = "shirt_split_red",
            category = "CAMISETAS",
            name = "ZX Vanguard Crimson",
            primaryColorHex = 0xFF8B0000,
            accentColorHex = 0xFF1B1B26,
            isLocked = false,
            levelReq = 10
        ),
        AvatarClothingItem(
            id = "shirt_gold_zx",
            category = "CAMISETAS",
            name = "ZX Golden Dominator",
            primaryColorHex = 0xFF1E1909,
            accentColorHex = 0xFFF59E0B,
            isLocked = true,
            levelReq = 15
        ),
        AvatarClothingItem(
            id = "shirt_cyan_zonex",
            category = "CAMISETAS",
            name = "Riverside Stealth Blue",
            primaryColorHex = 0xFF0A1528,
            accentColorHex = 0xFF38BDF8,
            isLocked = true,
            levelReq = 16
        ),
        AvatarClothingItem(
            id = "shirt_purple_zx",
            category = "CAMISETAS",
            name = "Phantom Sector Violet",
            primaryColorHex = 0xFF1A0A28,
            accentColorHex = 0xFFA855F7,
            isLocked = true,
            levelReq = 18
        ),
        AvatarClothingItem(
            id = "shirt_green_zonex",
            category = "CAMISETAS",
            name = "Highlands Toxic Green",
            primaryColorHex = 0xFF0A2012,
            accentColorHex = 0xFF22C55E,
            isLocked = true,
            levelReq = 20
        ),
        AvatarClothingItem(
            id = "shirt_carbon_gold",
            category = "CAMISETAS",
            name = "Apex Carbon Overlord",
            primaryColorHex = 0xFF18181B,
            accentColorHex = 0xFFD4AF37,
            isLocked = true,
            levelReq = 25
        )
    )

    val achievements: List<Achievement> = listOf(
        Achievement(
            id = "ach_conquest",
            title = "CONQUISTADOR",
            description = "Conquiste 50 territórios",
            current = 50,
            target = 50,
            iconType = "flag"
        ),
        Achievement(
            id = "ach_marathon",
            title = "MARATONISTA",
            description = "Corra 100 km no total",
            current = 100,
            target = 100,
            iconType = "shoe"
        ),
        Achievement(
            id = "ach_burner",
            title = "QUEIMADOR",
            description = "Queime 10.000 kcal",
            current = 10000,
            target = 10000,
            iconType = "flame"
        ),
        Achievement(
            id = "ach_invader",
            title = "INVASOR",
            description = "Ataque 10 territórios",
            current = 10,
            target = 10,
            iconType = "swords"
        )
    )

    fun selectShirt(shirtId: String) {
        _userProfile.update { it.copy(selectedShirtId = shirtId) }
    }

    fun updateBio(newBio: String) {
        _userProfile.update { it.copy(bio = newBio) }
    }

    fun registerNewUser(fullName: String, username: String, email: String) {
        _userProfile.update {
            it.copy(
                fullName = fullName.ifBlank { "Alexandre Silva" },
                username = username.ifBlank { "CORREDOR_ZX" },
                email = email.ifBlank { "corredor@zonex.app" }
            )
        }
    }

    fun startRunSession() {
        _runStats.value = LiveRunStats(
            isRunning = true,
            isPaused = false,
            elapsedSeconds = 42 * 60 + 15,
            distanceKm = 5.23,
            paceMinutesPerKm = "8:04",
            speedKmh = 7.4,
            caloriesBurned = 542,
            heartBpm = 138,
            heartZone = 4,
            steps = 7842,
            territoryCellsCaptured = 6
        )
    }

    fun pauseRunSession() {
        _runStats.update { it.copy(isPaused = true) }
    }

    fun resumeRunSession() {
        _runStats.update { it.copy(isPaused = false) }
    }

    fun tickRunSimulation() {
        _runStats.update { current ->
            if (!current.isRunning || current.isPaused) return@update current
            val newSeconds = current.elapsedSeconds + 1
            val deltaDist = 0.0021 + (Random.nextDouble() * 0.0005)
            val newDist = current.distanceKm + deltaDist
            val newSteps = current.steps + Random.nextInt(1, 3)
            val newCalories = current.caloriesBurned + if (newSeconds % 5L == 0L) 1 else 0
            val speedOscillation = 7.2 + (Random.nextDouble() * 0.6)
            val bpmOscillation = Random.nextInt(136, 142)
            val newSafe = if (current.safeZoneSecondsRemaining > 0) current.safeZoneSecondsRemaining - 1 else 0

            current.copy(
                elapsedSeconds = newSeconds,
                distanceKm = ((newDist * 100.0).toLong() / 100.0),
                speedKmh = ((speedOscillation * 10.0).toLong() / 10.0),
                steps = newSteps,
                caloriesBurned = newCalories,
                heartBpm = bpmOscillation,
                safeZoneSecondsRemaining = newSafe
            )
        }
    }

    fun applyConquestReward(reward: TerritoryConquestReward = TerritoryConquestReward()) {
        _userProfile.update { current ->
            val newXp = current.currentXp + reward.xpGained
            val newCoins = current.zxCoins + reward.zxGained
            val newTerritories = current.territoriesCount + reward.cellsGained
            val updatedDistricts = current.districtBreakdown.map { d ->
                if (d.name.contains("ZONEX CITY")) d.copy(cells = d.cells + reward.cellsGained) else d
            }
            current.copy(
                currentXp = newXp,
                zxCoins = newCoins,
                territoriesCount = newTerritories,
                districtBreakdown = updatedDistricts
            )
        }
        _runStats.update { it.copy(isRunning = false, isPaused = false) }
    }

    fun formatSecondsToHhMmSs(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
