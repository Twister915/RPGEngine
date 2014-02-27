package net.cogzmc.rpgengine;

import lombok.Getter;
import net.cogzmc.rpgengine.actions.ActionMasterListener;
import net.cogzmc.rpgengine.modular.Module;
import net.cogzmc.rpgengine.player.RPGPlayerManager;

public final class CoreModule extends Module {
    @Getter private static CoreModule instance;
    @Getter private RPGPlayerManager playerManager;
    @Override
    public void onEnable() {
        CoreModule.instance = this;
        this.playerManager = registerEvents(new RPGPlayerManager());
        registerEvents(new ActionMasterListener()).load();
    }

    @Override
    public void onDisable() {
        CoreModule.instance = null;
    }

    @Override
    public String getName() {
        return "core";
    }
}
