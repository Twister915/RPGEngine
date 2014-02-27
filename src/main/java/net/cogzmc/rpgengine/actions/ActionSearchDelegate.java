package net.cogzmc.rpgengine.actions;

import java.util.List;

public interface ActionSearchDelegate {
    public List<Action> search(ActionStack stack);
    public boolean matches(Action action);
}
