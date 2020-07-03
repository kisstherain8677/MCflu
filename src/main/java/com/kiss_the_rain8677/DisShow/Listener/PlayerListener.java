package com.kiss_the_rain8677.DisShow.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    //玩家进入服务器事件
    @EventHandler
    public  void PlayerJoin(PlayerJoinEvent event){
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"),0,100,1));

    }

    //测试 可删
    @EventHandler
    public void PlayerUse(PlayerInteractEvent event){
        if(event.getAction()== Action.RIGHT_CLICK_BLOCK||
        event.getAction()==Action.RIGHT_CLICK_AIR){
            //右键点击哪里都触发
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getItemInHand();
            if(itemInHand!=null){
                int amount=itemInHand.getAmount();
                itemInHand.setAmount(amount+1);
                player.setItemInHand(itemInHand);
                player.sendMessage("数量已经增加一个");
            }
        }
    }



}
