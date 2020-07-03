package com.kiss_the_rain8677.DisShow;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DisShow extends JavaPlugin  {
     private HashMap<UUID,Player> playerList;
     private double minDis;
     private Player mainPlayer;


    @Override
    public void onEnable() {
        this.getCommand("showdis").setExecutor(new MyCommand(this));
        this.getCommand("infect").setExecutor(new MyCommand(this));
        this.getCommand("showMaxHealth").setExecutor(new MyCommand(this));
        getLogger().info("DisTest enable!");
        //new task 20L one time
        BukkitTask task=new UpdateInfo(this).runTaskTimer(this, 0, 20L);

    }


    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public double getMinDis() {
        return minDis;
    }

    public void setMinDis(double minDis) {
        this.minDis = minDis;
    }

    public HashMap<UUID, Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(HashMap<UUID, Player> playerList) {
        this.playerList = playerList;
    }
}