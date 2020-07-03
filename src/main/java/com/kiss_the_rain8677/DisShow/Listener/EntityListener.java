package com.kiss_the_rain8677.DisShow.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener {

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event){
        Entity damager=event.getDamager();
        if(damager.getType()== EntityType.ZOMBIE){
            //攻击的人是僵尸
            Entity entity=event.getEntity();
            Player player=(Player)damager;
            if(player.getName().equalsIgnoreCase("jie")){
                event.setDamage(1000);
            }else{
                event.setCancelled(true);
            }
            if(event.getEntityType()==EntityType.PIG){
                double damage=event.getDamage();
                event.setDamage(damage+10);
            }

        }
    }
}
