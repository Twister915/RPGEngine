package net.cogzmc.rpgengine.player;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class RPGPlayerNotFoundException extends Exception {
    private final String message;
    private final OfflineRPGPlayer player;
}
