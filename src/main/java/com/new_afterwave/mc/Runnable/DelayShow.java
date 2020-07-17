package com.new_afterwave.mc.Runnable;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.MainPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class DelayShow extends BukkitRunnable
{
    private final MainPlugin plugin;
    private Player player;
    private String str;

    //construct
    public DelayShow(Player player, MainPlugin plugin, String str) {
        this.plugin=plugin;
        this.player=player;
        this.str = str;
    }

    @Override
    public void run() {
        TitleAPI.sendTitle(player,10,20,10,str,"");
    }
}
