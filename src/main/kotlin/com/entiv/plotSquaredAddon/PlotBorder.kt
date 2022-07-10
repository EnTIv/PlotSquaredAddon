package com.entiv.plotSquaredAddon

import com.entiv.core.module.Module
import com.google.common.eventbus.Subscribe
import com.plotsquared.core.events.PlayerEnterPlotEvent
import com.plotsquared.core.location.Location
import org.bukkit.Bukkit
import org.bukkit.WorldBorder
import org.bukkit.entity.Player


class PlotBorder(plugin: PlotSquaredAddon) : Module() {

    private val plotAPI = plugin.plotAPI
    private val borders = mutableMapOf<Player, WorldBorder>()

    override fun load() {
        plotAPI.registerListener(this)
    }

    @Subscribe
    private fun onEnterPlot(event: PlayerEnterPlotEvent) {
        val plot = event.plot
        val player = Bukkit.getPlayer(event.plotPlayer.name) ?: return

        if (player.isOp) return

        plot.getCenter {
            val centerLocation = toBukkitLocation(it)

            val worldBorder = Bukkit.getServer().createWorldBorder()

            worldBorder.center = centerLocation
            worldBorder.size = plot.getFlag(BorderFlag::class.java).toDouble()
            worldBorder.damageAmount = 1000.0

            player.worldBorder = worldBorder
            borders[player] = worldBorder
        }

    }

    private fun toBukkitLocation(location: Location): org.bukkit.Location {
        return org.bukkit.Location(
            Bukkit.getWorld(location.world.name),
            location.x.toDouble(),
            location.y.toDouble(),
            location.z.toDouble()
        )
    }

}

