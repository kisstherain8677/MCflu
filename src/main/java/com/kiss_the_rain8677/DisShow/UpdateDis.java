package com.kiss_the_rain8677.DisShow;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateDis extends BukkitRunnable {
    private final DisShow plugin;
    private Player mainPlayer;//main player
    private double minDis;
    //construct
    public UpdateDis(DisShow plugin) {
        minDis=99999;
        this.plugin=plugin;
    }

    //logic exc each time
    @Override
    public void run() {
        //找主玩家
        findMainPlayerInServer("jie");
        //更新当前玩家信息
        updatePlayerList();
        //更新主玩家最小距离
        updateDis();

        plugin.getServer().broadcastMessage("the min dis from jie is"+minDis);

    }

    public void findMainPlayerInServer(String name){
        //find main player
        if(Bukkit.getServer().getPlayer(name)==null) {
            //can't find
            Bukkit.getLogger().info("finding jie...");
            return;
        }
        else {
            Bukkit.getLogger().info("found "+name+"!");
            mainPlayer=Bukkit.getServer().getPlayer(name);
        }
    }

    public void updatePlayerList(){
        for(Player player:Bukkit.getServer().getOnlinePlayers()){
            plugin.getPlayerList().put(player.getUniqueId(),player);
        }
    }

    //update min dis and player
    public void updateDis() {
        minDis=99999;
        double mx=mainPlayer.getLocation().getX();
        double my=mainPlayer.getLocation().getY();
        double mz=mainPlayer.getLocation().getZ();
        //find all player
        HashMap<UUID, Player> playerList = plugin.getPlayerList();
        for(Map.Entry<UUID,Player> entry:playerList.entrySet()){
            if(entry.getKey().equals(mainPlayer.getUniqueId())) continue;
            double px=entry.getValue().getLocation().getX();
            double py=entry.getValue().getLocation().getY();
            double pz=entry.getValue().getLocation().getZ();

            double currentDis=Math.sqrt((px-mx)*(px-mx)+(py-my)*(py-my)+(pz-mz)*(pz-mz));
            if(currentDis<minDis) {
                minDis=currentDis;
            }
        }

    }



}