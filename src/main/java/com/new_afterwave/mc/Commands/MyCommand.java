package com.new_afterwave.mc.Commands;/*
package com.jie.mc.commands;

import com.jie.mc.Gui.InventoryGUI;
import com.jie.mc.MainPlugin;
import com.jie.mc.Util.TitleApi;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MyCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public  MyCommand(MainPlugin plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("renqi")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("up")) {
                        if (player.hasPermission("renqi.up")) {
                            InventoryGUI.PlayerrenqiGui(player);
                            */
/*Location location = player . getLocation();
                            player . sendMessage(  "X坐标:"+location . getX()+"Y坐标:"+location. getY()+"Z坐标:"+location. getZ()) ;
                            Location world = new Location(Bukkit.getWorld("world"), 10, 100, 10);
                            player.teleport(world);*//*

                        } else {
                            sender.sendMessage("§e没有renqi.up权限");
                        }
                    } else if (args[0].equalsIgnoreCase("down")) {
                        if (player.hasPermission("renqi.down")) {
                            if(args[1].equalsIgnoreCase("1")) {
                                System.out.println("=============");
                                TitleApi.sendTitle(player,5,4,5,"大标题","小标题");
                            }else if(args[1].equalsIgnoreCase("2"))
                                TitleApi.sendTitle(player,5,4,5,"大大标题","小小标题");
                            if(args[1].equalsIgnoreCase("3")) {
                                new BukkitRunnable() {
                                    int s = 10;

                                    @Override
                                    public void run() {
                                        s--;
                                        if (s == 0) {
                                            cancel();
                                        } else {
                                            Location location = player.getLocation();
                                            for (int degree = 0; degree < 360; degree++) {
                                                double radians = Math.toRadians(degree);
                                                double x = Math.cos(radians);
                                                double y = Math.sin(radians);
                                                 location.add(x, s, y);
                                                location.getWorld().playEffect(location, Effect.HAPPY_VILLAGER, 1);
                                                location.subtract(x, s, y);
                                            }
                                        }


                                    }

                                    ;
                                }.runTaskTimer(plugin, 0, 20);
                            }
                            */
/*World world = player.getWorld();
                            Bat bat = (Bat)world.spawnEntity(player.getLocation(), EntityType.BA T);
                            bat.setCustomNameVisible(true);
                            bat.setCustomName("吸血鬼");*//*

 */
/*if(args[1].equalsIgnoreCase("3")) {
                                Location location=player.getLocation();
                                for (int degree = 0; degree <360 ;degree++) {
                                    double radians = Math.toRadians(degree);
                                    double x= Math.cos(radians);
                                    double y= Math.sin(radians);
                                    location.add(x,0,y);
                                    location.getWorld().playEffect(location, Effect.HAPPY_VILLAGER,1);
                                    location.subtract(x,0,y);
                                }
                            }*//*

                        } else {
                            sender.sendMessage("§e没有renqi.down权限");
                        }
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (player.hasPermission("renqi.add")) {

                        } else {
                            sender.sendMessage("§e没有renqi.add权限，管理员才有这个权限");
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (player.hasPermission("renqi.remove")) {
                        } else {
                            sender.sendMessage("§e没有renqi.remove权限，管理员才有这个权限");
                        }

                    }
                    else if (args[0].equalsIgnoreCase("set")) {
                        if (player.hasPermission("renqi.set")) {
                        } else {
                            sender.sendMessage("§e没有renqi.set权限，管理员才有这个权限");
                        }
                        sender.sendMessage("§设置§e成功");
                    }
                    */
/*if (args[0].equalsIgnoreCase("add")) {

                        for (Player aplayer : Bukkit.getServer().getOnlinePlayers())
                            aplayer.getLocation();
                        JsonMessage.sendRawJson("[\"\",{\"text\":\"点我访问百度\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.baidu.com/\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[\"\",{\"text\":\"我是悬浮内容\",\"bold\":true,\"color\":\"aqua\"}]}}]", player);
                        sender.sendMessage("§d增加人气§e成功");
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        sender.sendMessage("§d减少人气§e成功");

                    }*//*

                }


            }
        } else {
            sender.sendMessage("服务器命令行不能用这个命令");
        }
        return true;
    }
}
*/


import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.Gui.ShopGUI;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Util.PluginInfo;
import com.new_afterwave.mc.VexView.FluGui;
import com.new_afterwave.mc.dao.MySQLManager;
import com.new_afterwave.mc.entity.Disinfector;
import com.new_afterwave.mc.entity.N95;
import com.new_afterwave.mc.entity.Ventilator;
import lk.vexview.api.VexViewAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MyCommand implements CommandExecutor
{
    private final MainPlugin plugin;

    public MyCommand(MainPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (command.getName().equalsIgnoreCase("showDis"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            player.sendMessage("你当前的最短距离是" + PluginInfo.getMinDis());
            return true;
        }

        else if (label.equalsIgnoreCase("setMaxHealth"))
        {
            if (args.length == 0)
            {
                sender.sendMessage("请指定玩家姓名");
            }
            else if (args.length == 1)
            {
                sender.sendMessage("请指定设定的上限");
            }
            else if (args.length == 2)
            {
                //调用感染函数
                StateControl control = new StateControl(plugin);
                control.setPlayerMaxHealth(args[0], Double.valueOf(args[1]));
                plugin.getServer().getWorld("world");
            }
            else
            {
                sender.sendMessage("参数过多");
            }
        }

        else if (label.equalsIgnoreCase("infect"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            if (args.length == 0)
            {
                player.sendMessage("请指定玩家姓名");
            }
            else if (args.length > 1)
            {
                player.sendMessage("参数过多");
            }
            else
            {
                StateControl stateControl = new StateControl(plugin);
                stateControl.infectPlayer(player, 100.0);
            }
        }

        else if (label.equalsIgnoreCase("disInfect"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            if (args.length == 0)
            {
                player.sendMessage("请指定玩家姓名");
            }
            else if (args.length > 1)
            {
                player.sendMessage("参数过多");
            }
            else
            {
                StateControl stateControl = new StateControl(plugin);
                stateControl.disInfectPlayer(player);
            }
        }

        else if (label.equalsIgnoreCase("showMaxHealth"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            if (args.length == 0)
            {
                player.sendMessage("请指定查询的玩家姓名");
            }
            else if (args.length == 1)
            {
                StateControl control = new StateControl(plugin);
                double mh = control.getPlayerMaxHealth(args[0]);
                sender.sendMessage(String.valueOf(mh));

            }
            else
            {
                player.sendMessage("请只输入一个玩家");
            }

        }

        else if (label.equalsIgnoreCase("invent"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            ShopGUI.PlayerrenqiGui(player);

        }

        else if (label.equalsIgnoreCase("showHealthState"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;

            FluGui fluGui=new FluGui(PluginInfo.getAllHealthList().get(player.getUniqueId()));
            VexViewAPI.openGui(player, fluGui.getFluGui());
            player.sendMessage("成功打开健康状态表");
            return true;

        }

//        //防护模块插入
//        else if (label.equalsIgnoreCase("give"))
//        {
//            if (!(sender instanceof Player))
//            {
//                sender.sendMessage("你必须是一名玩家!");
//                return true;
//            }
//            if (args.length == 2)
//            {
//                Player player = (Player)sender;
//                if (args[0].equalsIgnoreCase("disinfector"))
//                {
//                    for (int i = 0; i < Integer.parseInt(args[1]); i++)
//                        player.getInventory().addItem(new Disinfector());
//                }
//                else if (args[0].equalsIgnoreCase("N95"))
//                {
//                    player.getInventory().addItem(new N95(Integer.parseInt(args[1])));
//                }
//                return true;
//            }
//        }

        else if (label.equalsIgnoreCase("saveHealth"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player)sender;
            if(args.length==0){
                //保存状态到数据库
                MySQLManager.savePlayerHealth(player);
                return true;
            }
            else{
                sender.sendMessage("参数太多");
                return false;
            }

        }


        else if(label.equalsIgnoreCase("vent")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("你必须是一名玩家!");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage("参数过少!");
                return false;
            }
            Player player = (Player) sender;
            Location location = player.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            Block block = player.getWorld().getBlockAt(x , y, z - 1);
            block.setType(Material.CONCRETE_POWDER);
            MySQLManager.insertVentilator(args[0], block.getLocation(), player);
            return true;
        }
        else if(command.getName().equalsIgnoreCase("showvents")){
            if(!(sender instanceof Player)){
                sender.sendMessage("你必须是一名玩家!");
                return false;
            }
            MySQLManager.showVentilators((Player) sender);
            return true;
        }
        return false;
    }

}
