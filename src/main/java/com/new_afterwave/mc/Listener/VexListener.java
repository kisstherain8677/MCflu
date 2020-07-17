package com.new_afterwave.mc.Listener;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Runnable.UpdateHUD;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.KeyBoardPressEvent;
import lk.vexview.event.VerificationFinishEvent;
import lk.vexview.hud.VexButtonShow;
import lk.vexview.hud.VexTextShow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.xml.bind.Marshaller;
import java.util.Arrays;

public class VexListener implements Listener {

    MainPlugin plugin;

    //获取plugin，注册事件
    public VexListener(MainPlugin plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void PlayerBoardPress(KeyBoardPressEvent event){
          int key=event.getKey();
          Player player=event.getPlayer();
          //player.sendMessage("按下的键盘id是"+key);
          if(key==35){
              player.performCommand("showHealthState");
          }
    }

    @EventHandler
    public void PlayerJoinFinish(VerificationFinishEvent event){
       new UpdateHUD(event.getPlayer()).runTaskTimerAsynchronously(plugin,0,20L);
        //VexButtonShow vexButtonShow=new VexButtonShow("001",-1,10,1, Arrays.asList("距离最近疑似传染源："+dis),0);

    }
}
