package com.new_afterwave.mc.Listener;

import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.LoginManager;
import com.new_afterwave.mc.VexView.LoginGui;
import lk.vexview.api.VexViewAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * @author by
 * @description:
 * @date 2020/7/3 14:35
 */
public class PlayerLoginManagerListener implements Listener
{
    //不让聊天
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        //这里不拦截玩家用命令
        if (event.getMessage().substring(0, 1).equals("/"))
            return;
        event.setCancelled(needCancelled(event.getPlayer().getName()));
    }

    //不让玩家移动
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        event.setCancelled(needCancelled(event.getPlayer().getName()));
    }

    //不让玩家跟别的东西交互，约等于屏蔽左右键
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        event.setCancelled(needCancelled(event.getPlayer().getName()));
    }

    //不让玩家打开背包，然而按E打得开
    @EventHandler
    public void onPlayerInventory(InventoryOpenEvent event)
    {
        event.setCancelled(needCancelled(event.getPlayer().getName()));
        event.getPlayer().closeInventory();
    }

    //不让玩家点背包的物品
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event)
    {
        event.setCancelled(needCancelled(event.getWhoClicked().getName()));
    }

    private boolean needCancelled(String playerName)
    {
        return !LoginManager.isLogin(playerName);
    }

    // 下面的两个监听用来修改玩家的登录状态
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event)
    {
        LoginManager.setUnLoginState(event.getPlayer().getName(), true);
        Player player = event.getPlayer();
        player.sendMessage("§a请使用/login命令登录！");
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.performCommand("login");
            }
        }.runTaskLater(MainPlugin.plugin, 20L);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event)
    {
        LoginManager.setUnLoginState(event.getPlayer().getName(), false);
    }
}
