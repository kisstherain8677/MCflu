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
        //find main player
        findMainPlayerInServer("jie");
        //update players info
        updatePlayerList();
        //update min dis from main player
        updateDis();



    }

    public void findMainPlayerInServer(String name){
        //find main player
        if(Bukkit.getServer().getPlayer(name)==null) {
            //can't find
            Bukkit.getLogger().info("正在查找"+name+"...");
            return;
        }
        else {
            Bukkit.getLogger().info("已经找到 "+name+"!");
            mainPlayer=Bukkit.getServer().getPlayer(name);
        }
    }

    public void updatePlayerList(){
        HashMap<UUID, Player> playerHashMap = new HashMap<>();
        for(Player player:Bukkit.getServer().getOnlinePlayers()){
            playerHashMap.put(player.getUniqueId(),player);
        }
        plugin.setPlayerList(playerHashMap);
    }

    //update min dis and player
    public void updateDis() {
        if(mainPlayer==null) return;
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
        plugin.getServer().broadcastMessage("距离 "+mainPlayer.getName()+" jie的最小距离是"+minDis);

    }



}