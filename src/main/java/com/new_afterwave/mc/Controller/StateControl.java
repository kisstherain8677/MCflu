package com.new_afterwave.mc.Controller;

import com.new_afterwave.mc.MainPlugin;

import com.new_afterwave.mc.Runnable.DelayDead;
import com.new_afterwave.mc.Runnable.InfectedPartical;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;


public class StateControl
{

    private MainPlugin plugin;

    public StateControl(MainPlugin plugin)
    {
        this.plugin = plugin;
    }

    //获取玩家最大生命
    public double getPlayerMaxHealth(String name)
    {

        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            if (player.getName().equals(name))
            {
                return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            }
        }
        System.out.println("没有找到指定用户");
        return -1;//代表没有找到输入用户
    }

    //设置玩家最大生命
    public void setPlayerMaxHealth(String name, double health)
    {
        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(name);
        Player player = offlinePlayer.getPlayer();
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }

    //使一个玩家完全恢复正常
    public void disInfectPlayer(Player player)
    {
        //获得健康状态
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        HealthList healthList = allHealthList.get(player.getUniqueId());
        if (healthList == null)
        {
            plugin.getServer().getLogger().info("找不到健康信息");
            return;
        }
        if (!healthList.isInfected())
        {
            plugin.getServer().getLogger().info("已经是健康的");
            return;
        }
        //更新状态
        HealthList.resetHealth(player);

        setPlayerMaxHealth(player.getName(), 20);

        allHealthList.replace(player.getUniqueId(), healthList);
        PluginInfo.setAllHealthList(allHealthList);

    }

    //使一个玩家感染，感染相关状态变化控制
    //接触传播与吃生肉传播
    public void infectPlayer(Player player, double infectRate)
    {
        //获得健康状态
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        HealthList healthList = allHealthList.get(player.getUniqueId());
        if (healthList == null)
        {
            plugin.getServer().getLogger().info("找不到健康信息");
            return;
        }
        //如果没有感染
        //已经感染又感染，可能是新加入的已感染玩家，延续死亡延迟
        if (healthList.isInfected())
        {
            player.sendMessage(healthList.isLoginInfect() + "");
            if (healthList.isLoginInfect())
            {
                new DelayDead(player, plugin).runTaskTimer(plugin,0L, 20L);
                //播放粒子特效
                InfectedPartical infectedPartical = new InfectedPartical(player, plugin);
                infectedPartical.startEffect();
                healthList.setLoginInfect(false);
            }
        }
        else
        {
            double random = Math.random() * 100;//生成[0,100)的double
            if (random <= infectRate)
            {
                player.sendMessage("你感染了");
                setPlayerMaxHealth(player.getName(), 20 * 0.7);
                player.sendMessage("你现在有30%几率在" + healthList.getDeadTime() / 20 + "秒内死亡");
                //new DelayDead(player, plugin).runTaskLater(plugin, healthList.getDeadTime());
                new DelayDead(player, plugin).runTaskTimer(plugin, 0L,20L);

                //更新状态
                healthList.setInfected(true);
                allHealthList.replace(player.getUniqueId(), healthList);
                PluginInfo.setAllHealthList(allHealthList);
                //播放粒子特效
                InfectedPartical infectedPartical = new InfectedPartical(player, plugin);
                infectedPartical.startEffect();
            }
        }
    }

    //玩家服药
    public boolean usePotion(Player player)
    {
        //获得健康状态
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        HealthList healthList = allHealthList.get(player.getUniqueId());
        if (healthList == null)
        {
            plugin.getServer().getLogger().info("找不到健康信息");
            return false;
        }
        healthList.setPotionTacked(true);
        allHealthList.replace(player.getUniqueId(), healthList);
        return true;
    }

    //使用呼吸机，更新状态
    public void useVentilator(Player player)
    {
        //获得健康状态
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        HealthList healthList = allHealthList.get(player.getUniqueId());
        if (healthList == null)
        {
            plugin.getServer().getLogger().info("找不到健康信息");
            return;
        }
        //更新
        if (healthList.isVentilated())
        {
            Bukkit.getConsoleSender().sendMessage("呼吸机状态不应为true");
        }
        else
        {
            healthList.setVentilated(true);
            allHealthList.replace(player.getUniqueId(), healthList);
        }
        //结算
    }
}
