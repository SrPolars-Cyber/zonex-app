package com.example.model

data class UserProfile(
    val username: String = "CORREDOR_ZX",
    val fullName: String = "Alexandre Silva",
    val email: String = "corredor@zonex.app",
    val title: String = "Conquistador",
    val clan: String = "CLÃ ZX TEAM",
    val runnerId: String = "ZX-7A3F2K",
    val level: Int = 12,
    val currentXp: Int = 2150,
    val maxXp: Int = 3500,
    val zxCoins: Int = 3250,
    val gems: Int = 150,
    val territoriesCount: Int = 36,
    val achievementsCount: Int = 152,
    val bio: String = "Corro por saúde, luto por território, vivo por desafios.",
    val totalDistanceKm: Double = 1248.0,
    val totalTimeFormatted: String = "108:45:32",
    val totalCalories: Int = 85742,
    val totalSteps: Int = 1582462,
    val selectedShirtId: String = "shirt_zonex_default",
    val healthAttr: Int = 34,
    val speedAttr: Int = 28,
    val enduranceAttr: Int = 30,
    val precisionAttr: Int = 26,
    val strengthAttr: Int = 22,
    val districtBreakdown: List<DistrictConquest> = listOf(
        DistrictConquest("ZONEX CITY (Centro)", 12, 0xFFFF1E27),
        DistrictConquest("RIVERSIDE", 8, 0xFF2563EB),
        DistrictConquest("HIGHLANDS", 7, 0xFF16A34A),
        DistrictConquest("INDUSTRIAL", 5, 0xFFD97706),
        DistrictConquest("OUTRAS", 4, 0xFF64748B)
    )
)

data class DistrictConquest(
    val name: String,
    val cells: Int,
    val colorHex: Long
)

data class AvatarClothingItem(
    val id: String,
    val category: String, // CAMISETAS, CALÇAS, JAQUETAS, CONJUNTOS
    val name: String,
    val primaryColorHex: Long,
    val accentColorHex: Long,
    val isLocked: Boolean = false,
    val levelReq: Int = 1
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val current: Int,
    val target: Int,
    val iconType: String // "flag", "shoe", "flame", "swords"
)

data class LiveRunStats(
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val elapsedSeconds: Long = 42 * 60 + 15, // 00:42:15 initial demo reference
    val distanceKm: Double = 5.23,
    val paceMinutesPerKm: String = "8:04",
    val speedKmh: Double = 7.4,
    val caloriesBurned: Int = 542,
    val heartBpm: Int = 138,
    val heartZone: Int = 4, // 1 to 5
    val steps: Int = 7842,
    val territoryCellsCaptured: Int = 6,
    val safeZoneSecondsRemaining: Int = 7 * 60 + 45
)

data class TerritoryConquestReward(
    val zxGained: Int = 250,
    val xpGained: Int = 150,
    val domainPointsGained: Int = 10,
    val cellsGained: Int = 6
)
