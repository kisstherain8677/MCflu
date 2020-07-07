package com.kiss_the_rain8677.DisShow.Listener;

import com.kiss_the_rain8677.DisShow.MainPlugin;
import com.kiss_the_rain8677.DisShow.HealthList;
import com.kiss_the_rain8677.DisShow.Contoller.StateControl;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {

    MainPlugin plugin;
    //获取plugin，注册事件
    public PlayerListener(MainPlugin plugin){
        this.plugin=plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    //玩家进入服务器事件
    @EventHandler
    public  void PlayerJoin(PlayerJoinEvent event){
        String name=event.getPlayer().getName();
        UUID uid=event.getPlayer().getUniqueId();
        event.getPlayer().sendMessage("欢迎玩家 "+name);

        //初始化健康状态表
        HealthList stateList = new HealthList(uid,name);
        HashMap<UUID, HealthList> allHealthList = plugin.getAllHealthList();
        //如果已经有了此玩家，清空,放入新信息
        if(allHealthList.containsKey(uid)){
            allHealthList.remove(uid);
        }
        allHealthList.put(uid,stateList);
        event.getPlayer().sendMessage("已更新健康信息 "+name);
        if(stateList.isInfected()){
            event.getPlayer().sendMessage("已感染");
        }else{
            event.getPlayer().sendMessage("未感染");
        }

        plugin.setAllHealthList(allHealthList);
    }

    //玩家重生事件
    @EventHandler
    public void PlayerReborn(PlayerRespawnEvent event){
        String name=event.getPlayer().getName();
        UUID uid=event.getPlayer().getUniqueId();
        event.getPlayer().sendMessage("你重生了 "+name);
        StateControl stateControl=new StateControl(plugin);
        //还原最大生命
        stateControl.setPlayerMaxHealth(name,20.0);
        //重置健康信息
        HealthList stateList = new HealthList(uid,name);
        HashMap<UUID, HealthList> allHealthList = plugin.getAllHealthList();
        //如果已经有了此玩家，清空,放入新信息
        if(allHealthList.containsKey(uid)){
            allHealthList.remove(uid);
        }
        allHealthList.put(uid,stateList);
    }



    @EventHandler
    public void PlayerUse(PlayerInteractEvent event){
        //获得主手中的物品材料
        Material ma=event.getPlayer().getInventory().getItemInMainHand().getType();
        //获取玩家名,id
        String playerName=event.getPlayer().getName();
        UUID uid=event.getPlayer().getUniqueId();
        //获得健康状态
        HashMap<UUID,HealthList>allHealthList=plugin.getAllHealthList();
        HealthList healthList=allHealthList.get(uid);
        //event.getPlayer().sendMessage("get healthList"+healthList.getPlayerName());

        //如果是生肉,而且未中毒
        if(!healthList.isInfected() && (ma.equals(Material.RAW_BEEF)||ma.equals(Material.RAW_CHICKEN)||
                ma.equals(Material.RAW_FISH)||ma.equals(Material.MUTTON))){
            //右键任何地方
            if(event.getAction()==Action.RIGHT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_AIR){
                    StateControl stateControl=new StateControl(plugin);
                    if(stateControl.infectPlayer(event.getPlayer(),70,30)){
                        healthList.setInfected(true);//状态设置为中毒
                        //更新
                        allHealthList.remove(uid);
                        allHealthList.put(uid,healthList);
                        plugin.setAllHealthList(allHealthList);
                    }

                }
            }
        }

    }


