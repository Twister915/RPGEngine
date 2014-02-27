package net.cogzmc.rpgengine.player;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Data
public class OfflineRPGPlayer {
    private final String uuid;

    public Player getPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().toString().equals(uuid)) return player;
        }
        return null;
    }

    public RPGPlayer getRGPPlayer() throws RPGPlayerNotFoundException {
        Player player = getPlayer();
        if (player == null) throw new RPGPlayerNotFoundException("The player is not online!", this);
        return RPGPlayer.from(player);
    }
}
