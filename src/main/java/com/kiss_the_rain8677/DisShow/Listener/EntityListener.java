package com.kiss_the_rain8677.DisShow.Listener;

import com.kiss_the_rain8677.DisShow.DisShow;
import com.kiss_the_rain8677.DisShow.StateControl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
            Player player=(Player)damager;
            if(player.getName().equalsIgnoreCase(plugin.getMainPlayer().getName())){
                //如果没有感染
                StateControl control=new StateControl(plugin);
                control.setPlayerMaxHealth(player.getName(),control.getPlayerMaxHealth(player.getName())*0.7);
            }

        }
    }
}
