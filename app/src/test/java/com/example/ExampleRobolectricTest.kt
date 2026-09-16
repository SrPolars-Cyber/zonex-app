package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ZonexRepository
import com.example.model.TerritoryConquestReward
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ZONEX", appName)
    }

    @Test
    fun `test repository default user profile and conquest rewards`() {
        val profile = ZonexRepository.userProfile.value
        assertEquals("CORREDOR_ZX", profile.username)
        assertEquals(12, profile.level)
        assertEquals(36, profile.territoriesCount)

        val initialCoins = profile.zxCoins
        val initialXp = profile.currentXp
        val initialTerritories = profile.territoriesCount

        ZonexRepository.applyConquestReward(
            TerritoryConquestReward(
                zxGained = 250,
                xpGained = 150,
                domainPointsGained = 10,
                cellsGained = 6
            )
        )

        val updatedProfile = ZonexRepository.userProfile.value
        assertEquals(initialCoins + 250, updatedProfile.zxCoins)
        assertEquals(initialXp + 150, updatedProfile.currentXp)
        assertEquals(initialTerritories + 6, updatedProfile.territoriesCount)
    }

    @Test
    fun `test run session simulation`() {
        ZonexRepository.startRunSession()
        val initialStats = ZonexRepository.runStats.value
        assertTrue(initialStats.isRunning)
        assertEquals(5.23, initialStats.distanceKm, 0.01)

        ZonexRepository.tickRunSimulation()
        val afterTick = ZonexRepository.runStats.value
        assertTrue(afterTick.elapsedSeconds > initialStats.elapsedSeconds)
        assertTrue(afterTick.distanceKm >= initialStats.distanceKm)
    }
}
