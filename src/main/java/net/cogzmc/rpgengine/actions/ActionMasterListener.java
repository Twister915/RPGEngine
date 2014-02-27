package net.cogzmc.rpgengine.actions;

import net.cogzmc.rpgengine.RPGEngine;
import net.cogzmc.rpgengine.player.RPGPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public final class ActionMasterListener implements Listener {
    private RPGPlayerManager manager;
    public void load() {
        manager = RPGEngine.getInstnace().getCoreModule().getPlayerManager();
    }
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        logAction(event.getPlayer(), event);
    }
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSneakToggle(PlayerToggleSneakEvent event) {
        logAction(event.getPlayer(), event);
    }
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        logAction(event.getPlayer(), event);
    }
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSprintToggle(PlayerToggleSprintEvent event) {
        logAction(event.getPlayer(), event);
    }
    private ActionStack getActionStackFor(Player player) {
        return manager.getOnlinePlayer(player).getActionStack();
    }
    private void logAction(Player player, Event event) {
        getActionStackFor(player).logAction(new Action(event, player.getInventory().getHeldItemSlot(), player.getItemInHand().clone()));
    }
}
