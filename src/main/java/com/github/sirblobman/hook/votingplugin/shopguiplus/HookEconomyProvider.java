package com.github.sirblobman.hook.votingplugin.shopguiplus;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.bencodez.votingplugin.user.UserManager;
import com.bencodez.votingplugin.user.VotingPluginUser;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.provider.economy.EconomyProvider;

public final class HookEconomyProvider extends EconomyProvider {
    private final HookPlugin plugin;

    public HookEconomyProvider(HookPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
    }

    public void register() {
        ShopGuiPlusApi.registerEconomyProvider(this);
    }

    @Override
    public String getName() {
        return this.plugin.getName();
    }

    @Override
    public double getBalance(Player player) {
        VotingPluginUser user = getUser(player);
        return user.getPoints();
    }

    @Override
    public void deposit(Player player, double amount) {
        int roundedAmount = (int) Math.floor(amount);
        Runnable task = () -> {
            VotingPluginUser user = getUser(player);
            user.addPoints(roundedAmount);
        };

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskAsynchronously(this.plugin, task);
    }

    @Override
    public void withdraw(Player player, double amount) {
        int roundedAmount = (int) Math.floor(amount);
        Runnable task = () -> {
            VotingPluginUser user = getUser(player);
            user.removePoints(roundedAmount);
        };

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskAsynchronously(this.plugin, task);
    }
    
    private VotingPluginUser getUser(Player player) {
        UserManager userManager = UserManager.getInstance();
        return userManager.getVotingPluginUser(player);
    }
}
