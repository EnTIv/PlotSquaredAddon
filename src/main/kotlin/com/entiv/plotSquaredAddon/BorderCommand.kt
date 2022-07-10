package com.entiv.plotSquaredAddon

import com.entiv.core.command.CommandException
import com.entiv.core.command.CompositeCommand
import com.entiv.core.utils.infoPrefix
import com.entiv.core.utils.sendMessage
import com.plotsquared.core.plot.Plot
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BorderCommand(parent: CompositeCommand) : CompositeCommand(
    name = "border",
    description = "管理玩家的地皮边界",
    usage = "PlotSquaredAddon border set <玩家> <边界大小>",
    parent = parent
) {
    init {
        addSubcommand(SetCommand(this))
        addSubcommand(AddCommand(this))
    }
}

class SetCommand(parent: BorderCommand) : CompositeCommand(
    name = "set",
    description = "设置地皮边界",
    usage = "PlotSquaredAddon border set <玩家> <边界大小>",
    parent = parent
) {
    override fun onCommand() {
        setPlayerPlotBorder(sender, findPlayer(0), findInt(1))
    }
}

class AddCommand(parent: BorderCommand) : CompositeCommand(
    name = "add",
    description = "增加玩家的地皮边界",
    usage = "PlotSquaredAddon border add <玩家> <边界大小>",
    parent = parent
) {
    override fun onCommand() {
        val player = findPlayer(0)
        val size = findInt(1)

        val oldSize = getPlayerBorderSize(player)

        setPlayerPlotBorder(sender, player, oldSize + size)
    }
}

private fun setPlayerPlotBorder(sender: CommandSender, target: Player, size: Int) {

    if (size <= 0) {
        throw CommandException("边界大小必须大于 0")
    }

    val plot = getPlayerPlot(target)

    plot.setFlag(BorderFlag(size))

    sendMessage(sender, "$infoPrefix 已将玩家 ${target.name} 的地皮边界设置为 $size")
}

private fun getPlayerBorderSize(player: Player): Int {

    val plot = getPlayerPlot(player)

    return plot.getFlag(BorderFlag::class.java)
}

private fun getPlayerPlot(player: Player): Plot {
    val plugin = PlotSquaredAddon.instance

    val plotAPI = plugin.plotAPI

    val plotPlayer = plotAPI.wrapPlayer(player.uniqueId) ?: throw CommandException("无法将玩家 ${player.name} 转为地皮玩家")

    return plotAPI.getPlayerPlots(plotPlayer).firstOrNull() ?: throw CommandException("玩家 ${player.name} 没有地皮")
}