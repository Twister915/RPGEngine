package net.cogzmc.rpgengine.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import net.cogzmc.rpgengine.actions.ActionStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@EqualsAndHashCode(callSuper = false)
@Data
public final class RPGPlayer extends OfflineRPGPlayer {
    @NonNull private final ActionStack actionStack = new ActionStack(15);
    private final String username;

    public RPGPlayer(String name, String uuid) {
        super(uuid);
        this.username = name;
    }

    public static RPGPlayer from(Player bukkitPlayer) {
        return new RPGPlayer(bukkitPlayer.getName(), bukkitPlayer.getUniqueId().toString());
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayerExact(username);
    }
}
