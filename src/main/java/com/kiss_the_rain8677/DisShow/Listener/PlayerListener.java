package com.kiss_the_rain8677.DisShow.Listener;

import com.kiss_the_rain8677.DisShow.DisShow;
import com.kiss_the_rain8677.DisShow.HealthList;
import com.kiss_the_rain8677.DisShow.StateControl;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Random;

public class PlayerListener implements Listener {

    DisShow plugin;
    //获取plugin，注册事件
    public PlayerListener(DisShow plugin){
        this.plugin=plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    //玩家进入服务器事件
    @EventHandler
    public  void PlayerJoin(PlayerJoinEvent event){
        String name=event.getPlayer().getName();
        event.getPlayer().sendMessage("欢迎玩家 "+name);

        //初始化健康状态表
        HealthList stateList = new HealthList(name);
        HashMap<String, HealthList> allHealthList = plugin.getAllHealthList();
        //如果已经有了此玩家，清空
        if(allHealthList.containsKey(name)){
            allHealthList.remove(name);
        }
        else{
            allHealthList.put(name,stateList);
        }
        //event.getPlayer().sendMessage("已更新健康信息 "+name);
        plugin.setAllHealthList(allHealthList);
    }



    @EventHandler
    public void PlayerUse(PlayerInteractEvent event){
        //获得主手中的物品材料
        Material ma=event.getPlayer().getInventory().getItemInMainHand().getType();
        //获取玩家名
        String playerName=event.getPlayer().getName();
        //获得健康状态
        HashMap<String,HealthList>allHealthList=plugin.getAllHealthList();
        HealthList healthList=allHealthList.get(playerName);
        event.getPlayer().sendMessage("get healthList"+healthList.getPlayerName());


        //如果是生肉,而且未中毒
        if(!healthList.isInfected() && (ma.equals(Material.RAW_BEEF)||ma.equals(Material.RAW_CHICKEN)||
                ma.equals(Material.RAW_FISH)||ma.equals(Material.MUTTON))){
            //右键任何地方
            if(event.getAction()==Action.RIGHT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_AIR){
                //概率70%

                int max=100,min=1;
                double ran = (Math.random()*(max-min)+min);//生成[0,100)的double
                if(ran<70.0){
                    event.getPlayer().sendMessage("你感染了");
                    StateControl control = new StateControl(this.plugin);
                    double currentMaxHealth=control.getPlayerMaxHealth(playerName);
                    control.setPlayerMaxHealth(playerName,currentMaxHealth*0.7);
                    healthList.setInfected(true);//状态设置为中毒
                    //更新
                    allHealthList.remove(playerName);
                    allHealthList.put(playerName,healthList);
                    plugin.setAllHealthList(allHealthList);
                }
            }
        }

    }



}
