package com.new_afterwave.mc.Listener;

import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.Gui.ShopGUI;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Runnable.DefendModule;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import com.new_afterwave.mc.Util.VentilatorStructure;
import com.new_afterwave.mc.VexView.VentilatorGUI;
import com.new_afterwave.mc.dao.MySQLManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;


public class PlayerListener implements Listener
{

    MainPlugin plugin;

    //获取plugin，注册事件
    public PlayerListener(MainPlugin plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //玩家进入服务器事件
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String name = player.getName();
        UUID uid = player.getUniqueId();
        player.sendMessage("欢迎玩家 " + name);

        //将玩家信息从数据库中读入内存
        MySQLManager.loadPlayerHealth(player);
    }

    //玩家退出事件
    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event)
        throws Exception
    {
        Player player = event.getPlayer();
        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(player.getUniqueId());

        plugin.getLogger().info(player.getName() + "正在保存健康信息...");
//        //玩家信息保存到数据库
        MySQLManager.savePlayerHealth(offlinePlayer.getPlayer());

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (PluginInfo.getAllHealthList() != null)
                {
                    //玩家信息从内存清除
                    PluginInfo.getAllHealthList().remove(player.getUniqueId());
                }
            }
        }.runTaskLater(plugin, 40L);

    }

    //玩家重生事件
    @EventHandler
    public void PlayerReborn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        String name = player.getName();
        UUID uid = player.getUniqueId();
        StateControl stateControl = new StateControl(plugin);
        //还原最大生命
        stateControl.setPlayerMaxHealth(name, 20.0);
        //重置健康信息
        HealthList stateList = new HealthList(uid, name);
        HashMap<UUID, HealthList> allHealthList = PluginInfo.getAllHealthList();
        //如果已经有了此玩家，清空,放入新信息
        if (allHealthList.containsKey(uid))
        {
            allHealthList.remove(uid);
        }
        allHealthList.put(uid, stateList);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void AcquireTrade(PlayerInteractEntityEvent event)
    {
        if (event.getRightClicked().getType() == EntityType.VILLAGER)
        {
            System.out.println(event.getRightClicked().getCustomName());

            if(event.getRightClicked().getCustomName().equalsIgnoreCase("我真的是医生")){
                plugin.getLogger().info("准备在医生那买药");

                Player player = event.getPlayer();
                ShopGUI.buyGui(player);
                event.setCancelled(true);
            }
            else {
                plugin.getLogger().info("准备和傻子村民做买卖");
                Player player = event.getPlayer();
                ShopGUI.PlayerrenqiGui(player);
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void PlayerUse(PlayerInteractEvent event)
    {
        //获得主手中的物品材料
        Player player = event.getPlayer();
        Material ma = player.getInventory().getItemInMainHand().getType();
        //获取玩家名,id

        String playerName = player.getName();
        UUID uid = player.getUniqueId();

        //player.sendMessage("get healthList"+healthList.getPlayerName());

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            //如果是生肉或腐肉,而且未中毒
            if (ma.equals(Material.RAW_BEEF) || ma.equals(Material.RAW_CHICKEN) ||
                ma.equals(Material.RAW_FISH) || ma.equals(Material.MUTTON) || ma.equals(Material.ROTTEN_FLESH))
            {
                StateControl stateControl = new StateControl(plugin);
                HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());
                stateControl.infectPlayer(player,healthList.getEatInfectRate());
            }

            //防御模块插入
            try
            {
                if (event.hasItem() && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("消毒剂"))
                {
                    HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());
                    if (healthList.isDisinfected())
                    {
                        event.getPlayer().sendMessage("消毒剂效果未结束");
                        event.getPlayer().performCommand("give disinfector 1");
                        return;
                    }
                    healthList.setDisinfected(true);
                    healthList.setDisinfectorTime(12000L);
                    new DefendModule(player.getUniqueId(), "disinfector").runTaskTimerAsynchronously(plugin, 0L, 20L);
                    event.getPlayer().sendMessage("使用了消毒剂");
                }
                else if (event.hasItem() && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("N95口罩"))
                {
                    HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());
                    if (healthList.isMasked())
                    {
                        event.getPlayer().sendMessage("N95口罩效果未结束");
                        return;
                    }
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    healthList.setMasked(true);
                    healthList.setMaskTime(12000L);
                    new DefendModule(player.getUniqueId(), "mask").runTaskTimerAsynchronously(plugin, 0L, 20L);
                    event.getPlayer().sendMessage("使用了N95口罩");
                }
                //医疗模块插入
                else if(event.hasItem() && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7肺炎药水")){
                    HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());
                    //喝肺炎药水
                    StateControl stateControl=new StateControl(plugin);
                    //没肺炎
                    if(!healthList.isInfected()){
                        event.getPlayer().sendMessage("你很健康，无需使用");
                        event.setCancelled(true);
                        return;
                    }
                    //已经服用
                    else if(healthList.isPotionTacked()){
                        event.getPlayer().sendMessage("你已经服用过了");
                        event.setCancelled(true);
                        return;
                    }
                    event.getPlayer().sendMessage("你服用了肺炎药水，时间结束后死亡概率下降到10%");
                    stateControl.usePotion(player);
                }
            }
            catch (NullPointerException e) { }

            if(!event.hasItem() && VentilatorStructure.isVentilator(event.getClickedBlock())){
                VentilatorGUI.show(player);
            }
        }

    }
}




