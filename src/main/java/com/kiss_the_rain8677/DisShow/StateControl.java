package com.kiss_the_rain8677.DisShow;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class StateControl {

    private DisShow plugin;

    public StateControl(DisShow plugin) {
        this.plugin = plugin;
    }

    //
   public double getPlayerMaxHealth(String name){

       for(Map.Entry<UUID,Player> entry:plugin.getPlayerList().entrySet()){
           if(entry.getValue().getName().equals(name)){
               return this.plugin.getMainPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
           }
       }
       System.out.println("没有找到指定用户");
       return -1;//代表没有找到输入用户
    }

    public void setPlayerMaxHealth(String name,double health){
        for(Map.Entry<UUID,Player> entry:plugin.getPlayerList().entrySet()){
            if(entry.getValue().getName().equals(name)){
                this.plugin.getMainPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
                return;
            }
        }
        System.out.println("没有找到指定用户");
        return;
    }

}
