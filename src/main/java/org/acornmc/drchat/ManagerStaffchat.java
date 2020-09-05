package org.acornmc.drchat;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ManagerStaffchat {
    ConfigManager configManager;
    private static Set<UUID> staffchatToggled = new HashSet<>();

    public ManagerStaffchat(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static boolean isToggled(UUID uuid) {
        return staffchatToggled.contains(uuid);
    }

    public static void toggle(Player player) {
        if (staffchatToggled.contains(player.getUniqueId())) {
            staffchatToggled.remove(player.getUniqueId());
        } else {
            staffchatToggled.add(player.getUniqueId());
        }
    }

    public static void send(String message, Player player) {
        message = ChatManager.convertHex(message);
        message = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.broadcast(message, "drchat.staffchat");
        if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
            String finalMessage = ChatManager.convertHex(message);
            Bukkit.getServer().getScheduler().runTaskAsynchronously(DrChat.getProvidingPlugin(DrChat.class), () ->
                    DiscordSRV.getPlugin().processChatMessage(player, finalMessage, "staff-chat", false));
        }
    }





}