package com.github.sirblobman.hook.votingplugin.shopguiplus;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HookPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        printHookInformation("ShopGUIPlus");
        printHookInformation("VotingPlugin");
        new HookEconomyProvider(this).register();
    }

    private void printHookInformation(String pluginName) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin plugin = pluginManager.getPlugin(pluginName);
        if(plugin == null) {
            Logger logger = getLogger();
            logger.info("Failed to find a dependency: " + pluginName);
            return;
        }

        PluginDescriptionFile pluginDescription = plugin.getDescription();
        String pluginFullName = pluginDescription.getFullName();

        Logger logger = getLogger();
        logger.info("Successfully found a dependency: " + pluginFullName);
    }
}
