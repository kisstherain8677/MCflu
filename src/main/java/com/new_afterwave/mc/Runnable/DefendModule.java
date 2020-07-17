package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;


/**
 * @author by
 * @description:
 * @date 2020/7/10 19:11
 */
public class DefendModule extends BukkitRunnable
{
    private HealthList healthList;

    private String type;// mask | disinfector

    private UUID uuid;

    public DefendModule(UUID uuid, String type)
    {
        this.healthList = PluginInfo.getHealthList(uuid);
        this.type = type;
        this.uuid = uuid;
    }

    @Override
    public void run()
    {
        if (!MainPlugin.plugin.getServer().getOfflinePlayer(uuid).getPlayer().isOnline())
        {
            this.cancel();
        }
        if (type.equalsIgnoreCase("mask"))
        {
            long maskTime = healthList.getMaskTime();
            if (maskTime != 0L)
            {
                healthList.setMaskTime(maskTime - 20L);
                if (maskTime % 1200 == 0)
                    Bukkit.getPlayer(uuid).sendMessage("口罩持续时间还剩：" + maskTime / 20 + "秒。");
            }
            else
            {
                healthList.setMasked(false);
                this.cancel();
            }

        }
        else if (type.equalsIgnoreCase("disinfector"))
        {
            long disinfectorTime = healthList.getDisinfectorTime();

            if (disinfectorTime != 0L)
            {
                if (disinfectorTime % 1200 == 0)
                    Bukkit.getPlayer(uuid).sendMessage("消毒剂持续时间还剩：" + disinfectorTime / 20 + "秒。");
                healthList.setDisinfectorTime(disinfectorTime - 20L);
            }
            else
            {
                healthList.setDisinfected(false);
                this.cancel();
            }
        }
    }
}
