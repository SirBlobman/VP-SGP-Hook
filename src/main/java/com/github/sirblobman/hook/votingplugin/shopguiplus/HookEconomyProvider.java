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
        VotingPluginUser votingUser = UserManager.getInstance().getVotingPluginUser(player);
        return votingUser.getPoints();
    }

    @Override
    public void deposit(Player player, double amount) {
        int roundedAmount = (int) Math.floor(amount);
        Runnable task = () -> {
            VotingPluginUser votingUser = UserManager.getInstance().getVotingPluginUser(player);
            votingUser.addPoints(roundedAmount);
        };

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskAsynchronously(this.plugin, task);
    }

    @Override
    public void withdraw(Player player, double amount) {
        int roundedAmount = (int) Math.floor(amount);
        Runnable task = () -> {
            VotingPluginUser votingUser = UserManager.getInstance().getVotingPluginUser(player);
            votingUser.removePoints(roundedAmount);
        };

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskAsynchronously(this.plugin, task);
    }
}
