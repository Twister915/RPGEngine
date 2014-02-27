package net.cogzmc.rpgengine.actions;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.*;

@SuppressWarnings("UnusedDeclaration")
@Data
public final class ActionStack implements Iterable<Action> {
    @Getter(AccessLevel.PACKAGE) private List<Action> actions;
    private static final Long eventSequenceMaxTime = 1L;
    private final Integer capacity;

    {
        flush();
    }

    @Override
    public Iterator<Action> iterator() {
        return actions.iterator();
    }

    public void logAction(Action action) {
        this.actions.add(0, action);
        if (this.actions.size() >= capacity) pop();
    }

    public void flush() {
        actions = new LinkedList<>();
    }

    public void pop() {
        actions.remove(actions.size()-1);
    }

    public List<Action> searchForActions(ActionType... actionTypes) {
        List<Action> actions = new LinkedList<>();
        for (ActionType actionType : actionTypes) {
            actions.addAll(actionType.searchThrough(this));
        }
        Collections.sort(actions, new Comparator<Action>() {
            @Override
            public int compare(Action o1, Action o2) {
                return (int) (o1.getTime().getTime()-o2.getTime().getTime());
            }
        });
        return actions;
    }

    public List<Action> sequenceOfActions(ActionType... actionTypes) throws SequenceNotFoundException {
        List<Action> actions1 = searchForActions(actionTypes);
        Action[] actionsMatching = new Action[actionTypes.length];
        //typeAndActionIndex tracks the index for which action we're testing, and which spot in the "actionsMatching" array we should put the match that we find
        //These are the same because actionsMatching[] should match actionTypes[] in a test that validates using the .actionMatches on the type passing the action at the same index.
        for (int actionIndex = 0, typeAndActionIndex = 0, length = actionsMatching.length; actionIndex < actions1.size() || typeAndActionIndex > length; actionIndex++) {
            Action action = actions1.get(actionIndex);
            if (!actionTypes[typeAndActionIndex].actionMatches(action)) continue;
            if (typeAndActionIndex == 0) {
                actionsMatching[typeAndActionIndex] = action;
            } else {
                Action action1 = actionsMatching[typeAndActionIndex - 1];
                long l = action.getTime().getTime() - action1.getTime().getTime();
                if (l > eventSequenceMaxTime) continue;
                actionsMatching[typeAndActionIndex] = action;
            }
            typeAndActionIndex++;
            if (typeAndActionIndex == actionTypes.length) throw new SequenceNotFoundException("The sequence of actions could not be found!");
        }
        return Lists.newArrayList(actionsMatching);
    }
}
