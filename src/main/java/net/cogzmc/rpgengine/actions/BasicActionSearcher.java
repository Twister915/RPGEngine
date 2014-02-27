package net.cogzmc.rpgengine.actions;

import java.util.LinkedList;
import java.util.List;

public abstract class BasicActionSearcher implements ActionSearchDelegate {

    @Override
    public List<Action> search(ActionStack stack) {
        List<Action> actions = new LinkedList<>();
        for (Action action : stack) {
            if (testAction(action)) actions.add(action);
        }
        return actions;
    }

    @Override
    public boolean matches(Action action) {
        return testAction(action);
    }

    public abstract boolean testAction(Action action);
}
