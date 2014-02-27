package net.cogzmc.rpgengine;

import lombok.Getter;
import net.cogzmc.rpgengine.modular.ModularActionException;
import net.cogzmc.rpgengine.modular.Module;
import net.cogzmc.rpgengine.modular.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public final class RPGEngine extends JavaPlugin implements ModuleManager {

    @Getter private static RPGEngine instnace;
    @Getter private static ModuleManager moduleManager;

    @Getter private List<Module> modules;

    @Getter private CoreModule coreModule;

    @Override
    public void onEnable() {
        RPGEngine.instnace = this;
        RPGEngine.moduleManager = this;
        getLogger().info("Bootstrapping RPGEngine");
        try {
            saveDefaultConfig();
            loadUsingConfig(CoreModule.class);
            coreModule = getModuleOfType(CoreModule.class);
            if (coreModule == null) throw new Exception("No core module found!");
        } catch (Exception ex) {
            ex.printStackTrace();
            getLogger().info("Disabling RPGPlugin, exception thrown in main onEnable!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        getLogger().info("Enabled RPGEngine");
    }

    @Override
    public void onDisable() {
        for (Module module : modules) {
            try {
                unloadModule(module);
            } catch (ModularActionException e) {
                getLogger().info("Could not unload module " + module.getName());
                getLogger().severe(e.getMessage());
                e.printStackTrace();
            }
        }
        getLogger().info("Disabled RPGEngine");
    }

    public void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void loadModule(Module module) throws ModularActionException {
        module.enable();
        this.modules.add(module);
    }

    @Override
    public void unloadModule(Module module) throws ModularActionException {
        module.disable();
    }

    @Override
    public void loadUsingConfig(Class<? extends Module> moduleClass) throws ModularActionException {
        Module module = constructModule(moduleClass);
        loadModule(module);
    }

    @SafeVarargs
    @Override
    public final void loadUsingConfig(Class<? extends Module>... moduleClasses){
        for (Class<? extends Module> moduleClass : moduleClasses) {
            try {
                loadUsingConfig(moduleClass);
            } catch (ModularActionException e) {
                e.printStackTrace();
                getLogger().info("Could not load module!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Module> T getModuleOfType(Class<T> clazz) {
        for (Module module : this.modules) {
            if (module.getClass().equals(clazz)) return (T) module;
        }
        return null;
    }

    private Module constructModule(Class<? extends Module> module) throws ModularActionException {
        try {
            Constructor<? extends Module> constructor = module.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ModularActionException("Could not construct a new module!", e);
        }
    }
}
