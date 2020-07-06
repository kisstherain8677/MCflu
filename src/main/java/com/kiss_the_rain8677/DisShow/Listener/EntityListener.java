package com.kiss_the_rain8677.DisShow.Listener;

import com.kiss_the_rain8677.DisShow.DisShow;
import com.kiss_the_rain8677.DisShow.HealthList;
import com.kiss_the_rain8677.DisShow.StateControl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class EntityListener implements Listener {

    DisShow plugin;
    public EntityListener(DisShow plugin){
        this.plugin=plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event){

        Entity damager=event.getDamager();
        if(damager.getType()== EntityType.ZOMBIE){
            //攻击的人是僵尸
            Entity entity=event.getEntity();
            String  playerName=((Player)entity).getName();

            //获得健康状态
            HashMap<String, HealthList> allHealthList=plugin.getAllHealthList();
            HealthList healthList=allHealthList.get(playerName);

            if(playerName.equalsIgnoreCase(plugin.getMainPlayer().getName())){
                //如果没有感染
                if(healthList.isInfected()){
                    System.out.println("had infected");
                }else{
                    StateControl control=new StateControl(plugin);
                    control.setPlayerMaxHealth(playerName,control.getPlayerMaxHealth(playerName)*0.7);
                    //更新状态
                    allHealthList.remove(playerName);
                    allHealthList.put(playerName,healthList);
                    plugin.setAllHealthList(allHealthList);
                }

            }

        }
    }
}
