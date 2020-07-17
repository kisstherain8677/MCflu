package com.new_afterwave.mc.Listener;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Runnable.CurePartical;
import com.new_afterwave.mc.Runnable.EnableMove;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.PluginInfo;
import com.new_afterwave.mc.entity.COVIDPotion;
import lk.vexview.event.ButtonClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


//呼吸机监听
public class VentilatorListener implements Listener
{
    MainPlugin plugin;

    public VentilatorListener(MainPlugin plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //呼吸机按钮事件
    @EventHandler
    public void UseVentilator(ButtonClickEvent event)
        throws Exception
    {
        Player player = event.getPlayer();
        if (event.getButtonID().equals("vent_btn1"))
        {
            HealthList healthList = PluginInfo.getHealthList(player.getUniqueId());
            StateControl sc = new StateControl(plugin);
            //sc.infectPlayer(player);  测试用
            if (!healthList.isInfected())
            {
                player.sendMessage("你很健康，buddy");
                return;
            }
            //收钱
            int i;
            for(i = player.getInventory().getSize();i >= 0;i--){
                if(player.getInventory().getItem(i) != null){
                    if(player.getInventory().getItem(i).getType().equals(Material.EMERALD)){
                        player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
                        break;
                    }
                }
            }
            if(i < 0){
                player.sendMessage("你没有足够的绿宝石");
                return;
            }
            //关闭inv，玩家不能移动，展示Title，粒子特效
            player.closeInventory();
            new CurePartical(player, plugin).runTask(plugin);
            TitleAPI.sendTitle(player, 20, 100, 20, "治疗中", "");
            float speed = player.getWalkSpeed();
            player.setWalkSpeed(0);
            new EnableMove(plugin, player, speed).runTaskLater(plugin, 70);
            //状态更新
            sc.useVentilator(event.getPlayer());
            //结算
            if (Math.random() * 100 <= healthList.getDeadRate())
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        TitleAPI.sendTitle(player, 10, 40, 10, "很不幸，你因肺炎而死...", "");
                        player.setHealth(0);
                    }
                }.runTaskLater(plugin, 70);
            }
            else
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        sc.disInfectPlayer(player);
                        TitleAPI.sendTitle(player, 10, 40, 10, "很幸运，你活了下来", "");
                    }
                }.runTaskLater(plugin, 70);
            }

        }
        else if (event.getButtonID().equals("vent_btn2"))
        {
            Bukkit.broadcastMessage("购买药水成功");
            //购买药水
            /*if(玩家钱不够){
                player.sendMessage("没有足够的绿宝石");
                return;
            }*/
            //绿宝石-1
            //得到药水
            player.getInventory().addItem(new COVIDPotion());
        }
    }
}
