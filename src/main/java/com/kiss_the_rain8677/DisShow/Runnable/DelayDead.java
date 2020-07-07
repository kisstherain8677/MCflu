package com.kiss_the_rain8677.DisShow.Runnable;

import com.kiss_the_rain8677.DisShow.DisShow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayDead extends BukkitRunnable {

    private final DisShow plugin;
    private Player mainPlayer;//main player

    //construct
    public DelayDead(DisShow plugin) {
        this.plugin=plugin;
        this.mainPlayer=plugin.getMainPlayer();

    }

    @Override
    public void run() {
        mainPlayer.setHealth(0);
    }
}
