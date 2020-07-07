package com.kiss_the_rain8677.MCflu.Command;

import com.kiss_the_rain8677.MCflu.MainPlugin;
import com.kiss_the_rain8677.MCflu.GUI.InventoryGUI;
import com.kiss_the_rain8677.MCflu.Contoller.StateControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public  MyCommand(MainPlugin plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("showDis")){
            if(!(sender instanceof Player)){
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player) sender;
            player.sendMessage("你当前的最短距离是"+plugin.getMinDis());
            return true;
        }

        else if(label.equalsIgnoreCase("setMaxHealth")){
            if(!(sender instanceof Player)){
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)sender;
            if(args.length==0){
                player.sendMessage("请指定玩家姓名");
            }
            else if(args.length==1){
                player.sendMessage("请指定设定的上限");
            }
            else if(args.length==2){
                //调用感染函数
                StateControl control=new StateControl(plugin);
                control.setPlayerMaxHealth(args[0],Double.valueOf(args[1]));
                plugin.getServer().getWorld("world");
            }
            else{
                player.sendMessage("参数过多");
            }
        }

        else if(label.equalsIgnoreCase("showMaxHealth")){
            if(!(sender instanceof Player)){
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)sender;
            if(args.length==0){
                player.sendMessage("请指定查询的玩家姓名");
            }
            else if(args.length==1){
                StateControl control = new StateControl(plugin);
                double mh=control.getPlayerMaxHealth(args[0]);
                sender.sendMessage(String.valueOf(mh));

            }
            else{
                player.sendMessage("请只输入一个玩家");
            }

        }

        else if(label.equalsIgnoreCase("Invent")){
            if(!(sender instanceof Player)){
                sender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player=(Player)sender;
            InventoryGUI.PlayerrenqiGui(player);

        }

        return false;
    }



}
