package net.cogzmc.rpgengine.actions;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public enum ActionType {
    Right_Click(new BasicActionSearcher() {
        @Override
        public boolean testAction(Action action) {
            if (!(action.getCause() instanceof PlayerInteractEvent)) return false;
            PlayerInteractEvent interactEvent = (PlayerInteractEvent) action.getCause();
            return !(interactEvent.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR || interactEvent.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
        }
    }),
    Left_Click(new BasicActionSearcher() {
        @Override
        public boolean testAction(Action action) {
            if (!(action.getCause() instanceof PlayerInteractEvent)) return false;
            PlayerInteractEvent interactEvent = (PlayerInteractEvent) action.getCause();
            return !(interactEvent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR || interactEvent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK);
        }
    }),
    Sneak(new BasicActionSearcher() {
        @Override
        public boolean testAction(Action action) {
            return (!(action.getCause() instanceof PlayerToggleSneakEvent) && ((PlayerToggleSneakEvent)action.getCause()).isSneaking());
        }
    }),
    Move(new BasicActionSearcher() {
        @Override
        public boolean testAction(Action action) {
            return action.getCause() instanceof PlayerMoveEvent;
        }
    });
    ActionType(ActionSearchDelegate searchDelegate) {
       this.searchDelegate = searchDelegate;
    }
    private ActionSearchDelegate searchDelegate;
    public List<Action> searchThrough(ActionStack stack) {
        return searchDelegate.search(stack);
    }
    public boolean actionMatches(Action action) {
        return searchDelegate.matches(action);
    }
}
