package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateDis extends BukkitRunnable {
    private final MainPlugin plugin;
    //private Player mainPlayer;//main player
    //private double minDis;
    //construct
    public UpdateDis(MainPlugin plugin) {
        this.plugin=plugin;
//        this.mainPlayer=plugin.getMainPlayer();
//        minDis=99999;
    }

    //logic exc each time
    @Override
    public void run() {
        updateDis();
    }

    //更新所有在线玩家的最短距离
    public void updateDis(){
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        if(allHealthList==null)return;
        for(Player playerA:Bukkit.getServer().getOnlinePlayers()){
            double minDis=99999;
            for(Player playerB:Bukkit.getServer().getOnlinePlayers()){
                if(!playerA.getUniqueId().equals(playerB.getUniqueId()) &&
                    PluginInfo.getAllHealthList().get(playerB.getUniqueId()).isInfected()) {
                    Location locA = playerA.getLocation();
                    double temp = locA.distance(playerB.getLocation());
                    if (temp < minDis) minDis = temp;
                }
            }
            //将得到的minDis设置回HealthList
            if(allHealthList.containsKey(playerA.getUniqueId())){
                HealthList healthList = allHealthList.get(playerA.getUniqueId());
                healthList.setNearDis(minDis);
                allHealthList.replace(playerA.getUniqueId(),healthList);
                PluginInfo.setAllHealthList(allHealthList);
            }
        }
    }


}

