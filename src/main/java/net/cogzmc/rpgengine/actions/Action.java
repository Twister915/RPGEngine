package net.cogzmc.rpgengine.actions;

import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

@Data
public final class Action {
    private final Event cause;
    private final Integer slot;
    private final ItemStack itemInHand;
    private final Date time = new Date();
}
