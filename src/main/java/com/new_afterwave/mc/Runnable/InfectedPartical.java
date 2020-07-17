package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class InfectedPartical extends BukkitRunnable
{
    private final MainPlugin plugin;

    private Player player;//main player

    private HealthList healthList;

    //construct
    public InfectedPartical(Player player, MainPlugin plugin)
    {
        this.plugin = plugin;
        this.player = player;
        healthList = PluginInfo.getHealthList(player.getUniqueId());
    }

    @Override
    public void run()
    {
        try
        {
            if (!healthList.isInfected())
            {
                this.cancel();
            }
        }
        catch (NullPointerException e)
        {
            this.cancel();
        }

        if (player == null || !player.isOnline())
        {
            cancel();
        }

        //如果感染，且未死亡，播放粒子特效
        if (player.getHealth() > 0 && PluginInfo.getHealthList(player.getUniqueId()).isInfected())
        {
            Location playerLocation = player.getLocation();
            for (int degree = 0; degree < 360; degree += 2)
            {
                double radians = Math.toRadians(degree);
                double x = Math.cos(radians);
                double z = Math.sin(radians);
                playerLocation.add(x, 0D, z);//向量加
                playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 0);
                playerLocation.subtract(x, 0D, z);//向量减是为了起点还是原来的

            }
           // healthList.setDeadTime(healthList.getDeadTime() - 20L);
        }
        else
        {
            cancel();
        }
    }

    public void startEffect()
    {
        runTaskTimer(plugin, 0L, 20L);
    }

    public void closeEffect()
    {
        cancel();
    }
}
