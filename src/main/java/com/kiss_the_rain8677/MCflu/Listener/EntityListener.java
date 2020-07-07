package com.kiss_the_rain8677.MCflu.Listener;

import com.kiss_the_rain8677.MCflu.MainPlugin;
import com.kiss_the_rain8677.MCflu.HealthList;
import com.kiss_the_rain8677.MCflu.Contoller.StateControl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class EntityListener implements Listener {

    MainPlugin plugin;
    public EntityListener(MainPlugin plugin){
        this.plugin=plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event){

        Entity damager=event.getDamager();
        if(damager.getType()== EntityType.ZOMBIE || damager.getType()==EntityType.ZOMBIE_VILLAGER){
            //攻击的人是僵尸
            Entity entity=event.getEntity();
            String  playerName=((Player)entity).getName();

            //获得健康状态
            HashMap<UUID, HealthList> allHealthList=plugin.getAllHealthList();
            HealthList healthList=allHealthList.get(entity.getUniqueId());

            if(playerName.equalsIgnoreCase(plugin.getMainPlayer().getName())){
                //如果没有感染
                if(healthList.isInfected()){
                    entity.sendMessage("已经感染了");
                }else{
                    StateControl control=new StateControl(plugin);
                    control.infectPlayer((Player)entity,100,30);
                    //更新状态
                    allHealthList.remove(entity.getUniqueId());
                    allHealthList.put(entity.getUniqueId(),healthList);
                    plugin.setAllHealthList(allHealthList);
                }

            }

        }
    }
}
