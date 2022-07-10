package com.entiv.plotSquaredAddon

import com.entiv.core.command.DefaultCommand
import com.entiv.core.debug.warn
import com.entiv.core.plugin.InsekiPlugin
import com.plotsquared.core.PlotAPI
import com.plotsquared.core.plot.flag.GlobalFlagContainer
import org.bukkit.Bukkit

class PlotSquaredAddon : InsekiPlugin() {

    val plotAPI = PlotAPI()

    override fun onEnabled() {
        checkPlotSquared()

        registerCommand()

        GlobalFlagContainer.getInstance().addFlag(BorderFlag.BORDER_SIZE_DEFAULT)

        moduleManager.load(PlotBorder(this))
    }

    private fun checkPlotSquared() {
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
            warn("未安装 PlotSquared 插件，插件已卸载")
            Bukkit.getPluginManager().disablePlugin(this)
        }
    }

    private fun registerCommand() {
        val defaultCommand = DefaultCommand()

        defaultCommand.register()

        BorderCommand(defaultCommand).register()
    }

    companion object {
        val instance by lazy { InsekiPlugin.instance as PlotSquaredAddon }
    }

}
