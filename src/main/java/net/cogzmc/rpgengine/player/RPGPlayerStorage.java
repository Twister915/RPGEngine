package net.cogzmc.rpgengine.player;

import java.util.List;

public interface RPGPlayerStorage {
    public OfflineRPGPlayer getRPGPlayerFromUUID(String uuid) throws RPGPlayerNotFoundException;
    public void savePlayer(OfflineRPGPlayer offlineRPGPlayer) throws RPGPlayerNotFoundException;
    public List<OfflineRPGPlayer> getAllPlayers();
}
