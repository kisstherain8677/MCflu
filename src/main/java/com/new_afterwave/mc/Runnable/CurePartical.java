package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.MainPlugin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class CurePartical extends BukkitRunnable
{
    private final MainPlugin plugin;

    private Player player;

    public CurePartical(Player player, MainPlugin plugin)
    {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run()
    {
        Location playerLocation = player.getLocation();
        for(int degree=0;degree<360;degree++) {
            double radians = Math.toRadians(degree);
            double x = Math.cos(radians);
            double z = Math.sin(radians);
            playerLocation.add(x, 1D, z);//向量加
            playerLocation.getWorld().spawnParticle(Particle.CLOUD, playerLocation, 0);
            playerLocation.subtract(x, 1D, z);//向量减是为了起点还是原来的
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int degree=0;degree<360;degree++) {
            double radians = Math.toRadians(degree);
            double x = Math.cos(radians);
            double z = Math.sin(radians);
            playerLocation.add(x, 1D, z);//向量加
            playerLocation.getWorld().spawnParticle(Particle.CLOUD, playerLocation, 0);
            playerLocation.subtract(x, 1D, z);//向量减是为了起点还是原来的
        }
        cancel();
    }
}
