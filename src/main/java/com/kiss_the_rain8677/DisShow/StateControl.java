package com.kiss_the_rain8677.DisShow;

import com.kiss_the_rain8677.DisShow.Runnable.DelayDead;
import com.kiss_the_rain8677.DisShow.Runnable.InfectedPartical;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;

public class StateControl {

    private DisShow plugin;

    public StateControl(DisShow plugin) {
        this.plugin = plugin;
    }

    //
    public double getPlayerMaxHealth(String name) {

        for (Map.Entry<UUID, Player> entry : plugin.getPlayerList().entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return this.plugin.getMainPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            }
        }
        System.out.println("没有找到指定用户");
        return -1;//代表没有找到输入用户
    }

    public void setPlayerMaxHealth(String name, double health) {
        for (Map.Entry<UUID, Player> entry : plugin.getPlayerList().entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                this.plugin.getMainPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                return;
            }
        }
        System.out.println("没有找到指定用户");
        return;
    }

    //如果完成感染，返回true
    public boolean infectPlayer(Player player,double infectRate,double deadRate) {

        int max = 100, min = 1;
        double ran = (Math.random() * (max - min) + min);//生成[0,100)的double
        if (ran < infectRate) {
            player.sendMessage("你感染了");
            double currentMaxHealth = getPlayerMaxHealth(player.getName());
            setPlayerMaxHealth(player.getName(), currentMaxHealth * 0.7);
            player.sendMessage("你现在有30%几率在10分钟内死亡");
            if (ran < deadRate) {
                BukkitTask task = new DelayDead(plugin).runTaskLater(plugin, 12000L);
            }
            //播放粒子特效
            InfectedPartical infectedPartical=new InfectedPartical(plugin);
            infectedPartical.startEffect();

            return true;
        }else{
            return false;
        }
    }
}
