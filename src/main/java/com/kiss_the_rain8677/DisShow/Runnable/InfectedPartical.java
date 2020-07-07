package com.kiss_the_rain8677.DisShow.Runnable;

import com.kiss_the_rain8677.DisShow.MainPlugin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InfectedPartical extends BukkitRunnable {

    private final MainPlugin plugin;
    private Player player;//main player

    //construct
    public InfectedPartical(MainPlugin plugin) {
        this.plugin=plugin;
        this.player=plugin.getMainPlayer();

    }

    @Override
    public void run() {
        if(player==null || !player.isOnline()){
            cancel();
        }

        //如果感染，且未死亡，播放粒子特效
        if(player.getHealth()>0 && plugin.getAllHealthList().get(player.getUniqueId()).isInfected()) {
        Location playerLocation=player.getLocation();
        for(int degree=0;degree<360;degree++) {
            double radians = Math.toRadians(degree);
            double x = Math.cos(radians);
            double z = Math.sin(radians);
            playerLocation.add(x, 0D, z);//向量加
            playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 0);
            playerLocation.subtract(x, 0D, z);//向量减是为了起点还是原来的
            }
        }
        else{
            cancel();
        }
    }


    public void startEffect(){
        runTaskTimer(plugin,0L,10L);
    }

    public void closeEffect(){
        cancel();
    }
}
