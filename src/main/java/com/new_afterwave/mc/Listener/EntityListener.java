package com.new_afterwave.mc.Listener;

import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;


public class EntityListener implements Listener
{

    MainPlugin plugin;

    public EntityListener(MainPlugin plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event)
    {

        Entity damager = event.getDamager();
        if (damager.getType() == EntityType.ZOMBIE || damager.getType() == EntityType.ZOMBIE_VILLAGER)
        {
            //攻击的人是僵尸
            Entity entity = event.getEntity();
            if (entity instanceof Player)
            {
                Player player = (Player)entity;
                String playerName = player.getName();

                //获得健康状态
                HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
                HealthList healthList = allHealthList.get(player.getUniqueId());
                //如果没有感染
                if (healthList.isInfected())
                {
                    entity.sendMessage("已经感染了");
                }
                else
                {
                    StateControl control = new StateControl(plugin);
                    control.infectPlayer(player, healthList.getAttackInfectRate());
                }

            }

        }
    }
}
