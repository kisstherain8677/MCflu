package com.kiss_the_rain8677.DisShow;

import java.util.HashMap;
import java.util.UUID;

import com.kiss_the_rain8677.DisShow.Command.MyCommand;
import com.kiss_the_rain8677.DisShow.Listener.EntityListener;
import com.kiss_the_rain8677.DisShow.Listener.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class DisShow extends JavaPlugin  {
     private HashMap<UUID,Player> playerList;//玩家信息列表（系统自带）
     private HashMap<String, HealthList> allHealthList; //玩家健康表（插件新增）
     private double minDis;
     private Player mainPlayer;


    @Override
    public void onEnable() {
        //initial
        allHealthList=new HashMap<String,HealthList>();
        playerList=new HashMap<UUID,Player>();

        //Listener
        new PlayerListener(this);
        new EntityListener(this);

        //Command
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

    public HashMap<String, HealthList> getAllHealthList() {
        return allHealthList;
    }

    public void setAllHealthList(HashMap<String, HealthList> allHealthList) {
        this.allHealthList = allHealthList;
    }
}