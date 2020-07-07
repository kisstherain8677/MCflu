package com.kiss_the_rain8677.MCflu.Runnable;

import com.kiss_the_rain8677.MCflu.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateInfo extends BukkitRunnable {
    private final MainPlugin plugin;
    private Player mainPlayer;//main player
    private double minDis;
    //construct
    public UpdateInfo(MainPlugin plugin) {
        this.plugin=plugin;
        this.mainPlayer=plugin.getMainPlayer();
        minDis=99999;

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
        //以下是条件事件
    }

    public void findMainPlayerInServer(String name){
        //find main player
        if(Bukkit.getServer().getPlayer(name)==null) {
            //can't find
            //Bukkit.getLogger().info("正在查找"+name+"...");
            //System.out.println("正在查找"+name+"...");
            return;
        }
        else {
            //Bukkit.getLogger().info("已经找到 "+name+"!");
            //System.out.println("已经找到 "+name+"!");
            this.plugin.setMainPlayer(Bukkit.getServer().getPlayer(name));
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
        //plugin.getServer().broadcastMessage("距离 "+mainPlayer.getName()+" 的最小距离是"+minDis);
        //update the minDis in plugin
        plugin.setMinDis(minDis);

    }



}

