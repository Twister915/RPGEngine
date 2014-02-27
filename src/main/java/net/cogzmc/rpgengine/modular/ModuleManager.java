package net.cogzmc.rpgengine.modular;

import java.util.List;

public interface ModuleManager {
    public List<Module> getModules();
    public void loadModule(Module module) throws ModularActionException;
    public void unloadModule(Module module) throws ModularActionException;
    public void loadUsingConfig(Class<? extends Module> moduleClass) throws ModularActionException;
    public void loadUsingConfig(Class<? extends Module>... moduleClasses) throws ModularActionException;
    public<T extends Module> T getModuleOfType(Class<T> clazz);
}
