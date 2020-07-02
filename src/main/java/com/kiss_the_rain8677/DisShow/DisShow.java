package com.kiss_the_rain8677.DisShow;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DisShow extends JavaPlugin  {
     private HashMap<UUID,Player> playerList;

    @Override
    public void onEnable() {
        getLogger().info("DisTest enable!");
        //new task 20L one time
        BukkitTask task=new UpdateDis(this).runTaskTimer(this, 0, 20L);

    }

    public HashMap<UUID,Player> getPlayerList(){
        return playerList;
    }


}