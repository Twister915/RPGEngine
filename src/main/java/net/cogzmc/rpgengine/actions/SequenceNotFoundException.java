package net.cogzmc.rpgengine.actions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class SequenceNotFoundException extends Exception{
    private final String message;
}
