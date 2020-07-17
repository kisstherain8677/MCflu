package com.new_afterwave.mc.Runnable;

import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexTextShow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class UpdateHUD extends BukkitRunnable {
    Player player;
    public UpdateHUD(Player player){
        this.player=player;
    }
    @Override
    public void run() {
        HealthList healthList=PluginInfo.getAllHealthList().get(player.getUniqueId());
        if(healthList==null)
            return;
        double dis= healthList.getNearDis();
        double time=healthList.getDangerTime();
        VexTextShow vexTextShowDis=new VexTextShow("000",-1,10,1, Arrays.asList("距离最近疑似传染源："+dis),0);
        VexTextShow vexTextShowTime=new VexTextShow("001",-1,20,1, Arrays.asList("距离最近疑似传染源："+time/20+"s"),0);
        VexViewAPI.sendHUD(player,vexTextShowDis);
        VexViewAPI.sendHUD(player,vexTextShowTime);
    }
}
