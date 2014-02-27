package net.cogzmc.rpgengine.modular;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class ModularActionException extends Exception {
    private final String message;
    private final Exception e;
}
