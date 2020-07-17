package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.MainPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class EnableMove extends BukkitRunnable
{
    private final MainPlugin plugin;
    private Player player;
    private float speed;

    public EnableMove(MainPlugin plugin, Player player, float speed) {
        this.plugin = plugin;
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void run() {
        player.setWalkSpeed(speed);
    }
}
