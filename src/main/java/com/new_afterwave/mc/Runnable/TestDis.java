package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


//这个定时任务用于监测最短距离在给定阈值内保持的时间有多久,超过一定时间则感染
public class TestDis extends BukkitRunnable
{

    MainPlugin plugin;

    double minDis;//距离阈值

    long maxTime;//时间阈值

    //boolean firstTime;//判断是否是初始化

    public TestDis(MainPlugin plugin, double minDis, long maxTime)
    {
        this.plugin = plugin;
        this.minDis = minDis;
        this.maxTime = maxTime;
        //this.firstTime = true;
    }

    @Override
    public void run()
    {
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        if (allHealthList == null)
            return;

        for (Map.Entry<UUID, HealthList> entry : allHealthList.entrySet())
        {
            HealthList aHealthList = allHealthList.get(entry.getKey());

            if (aHealthList.getNearDis() > minDis)
            {
                aHealthList.setDangerTime(maxTime);
            }
            else
            {
                long leftTime = aHealthList.getDangerTime();
                if (leftTime <= 0)
                {
                    //戴口罩就不感染了
                    if (!allHealthList.get(plugin.getServer().getPlayer(entry.getKey()).getUniqueId()).isMasked())
                    {
                        //处于危险距离太久，感染
                        Player player = plugin.getServer().getPlayer(entry.getKey());
                        StateControl stateControl = new StateControl(plugin);
                        stateControl.infectPlayer(player, 100.0);
                    }
                    aHealthList.setDangerTime(0);
                }
                else
                {
                    leftTime -= 20L;
                    aHealthList.setDangerTime(leftTime);
                }
                //更新危险时间(注意是倒计时）
                allHealthList.replace(entry.getKey(), aHealthList);
                PluginInfo.setAllHealthList(allHealthList);

            }
        }
//        //输出dangertime
//        for (Player player : plugin.getServer().getOnlinePlayers())
//        {
//            if (player == null)
//                return;
//            try
//            {
//                if (allHealthList.get(player.getUniqueId()).getNearDis() <= 30)
//                {
//                    player.sendMessage("有玩家在附近！");
//                    player.sendMessage("你的危险距离:" + allHealthList.get(player.getUniqueId()).getNearDis());
//                    player.sendMessage("你的危险时间:" + allHealthList.get(player.getUniqueId()).getDangerTime());
//                }
//            }
//            catch (NullPointerException e) {}
//        }
    }

}
