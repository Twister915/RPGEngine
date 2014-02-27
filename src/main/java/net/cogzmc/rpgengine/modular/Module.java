package net.cogzmc.rpgengine.modular;

import lombok.AccessLevel;
import lombok.Getter;
import net.cogzmc.rpgengine.RPGEngine;
import org.bukkit.event.Listener;

public abstract class Module {
    @Getter(AccessLevel.PACKAGE) private RPGEngine rpgEngine;
    public final void enable() throws ModularActionException {
        try {
            onEnable();
        } catch (Exception e) {
            throw new ModularActionException("Could not load module " + getName() + "!", e);
        }
    }
    public final void disable() throws ModularActionException {
        try {
            onDisable();
        } catch (Exception e) {
            throw new ModularActionException("Could not unload module " + getName() + "!", e);
        }
    }

    protected final <T extends Listener> T registerEvents(T listener) {
        rpgEngine.registerEvents(listener);
        return listener;
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public abstract String getName();
}
