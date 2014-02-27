package net.cogzmc.rpgengine.player;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
@Data
public final class RPGPlayerManager implements Listener {
    private RPGPlayerStorage playerStorage;
    private List<RPGPlayer> onlineRPGPlayers;
    public OfflineRPGPlayer getOfflinePlayer(String uuid) throws RPGPlayerNotFoundException {
        return playerStorage.getRPGPlayerFromUUID(uuid);
    }
    public void reloadPlayers() {
        this.onlineRPGPlayers = new LinkedList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.onlineRPGPlayers.add(constructRPGPlayer(player));
        }
    }
    private RPGPlayer constructRPGPlayer(Player player) {
        return RPGPlayer.from(player);
    }

    public RPGPlayer getOnlinePlayer(Player player) {
        String name = player.getName();
        for (RPGPlayer onlineRPGPlayer : this.onlineRPGPlayers) {
            if (onlineRPGPlayer.getUsername().equals(name)) return onlineRPGPlayer;
        }
        return null;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        loadPlayer(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLeave(PlayerQuitEvent event) {
        unloadPlayer(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        unloadPlayer(event.getPlayer());
    }

    private void loadPlayer(Player player) {
        this.onlineRPGPlayers.add(constructRPGPlayer(player));
    }
    private void unloadPlayer(Player player) {
        this.onlineRPGPlayers.remove(getOnlinePlayer(player));
    }
}
