package com.new_afterwave.mc.Runnable;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
//import sun.applet.Main;


public class DelayDead extends BukkitRunnable
{

    private final MainPlugin plugin;

    private Player player;//main player

    //construct
    public DelayDead(Player player, MainPlugin plugin)
    {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run()
    {

        try
        {
            if (!PluginInfo.getHealthList(player.getUniqueId()).isInfected())
            {
                this.cancel();
            }
        }
        catch (NullPointerException e)
        {
            this.cancel();
        }

        HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());

        if(healthList.getDeadTime()>0){
            healthList.setDeadTime(healthList.getDeadTime() - 20L);
        }
        else{
            double random = Math.random() * 100;
            if (random <= PluginInfo.getHealthList(player.getUniqueId()).getDeadRate())
            {
                TitleAPI.sendTitle(player, 10, 40, 10, "很不幸，你因肺炎而死...", "");
                try
                {
                    Thread.sleep(1500);
                }
                catch (InterruptedException e) {e.printStackTrace();}
                player.setHealth(0);
            }
            else
            {
                new StateControl((MainPlugin)MainPlugin.plugin).disInfectPlayer(player);
                TitleAPI.sendTitle(player, 10, 40, 10, "很幸运，你活了下来", "");
            }
        }

    }
}
