package com.new_afterwave.mc.Commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.Util.LoginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author by
 * @description:
 * @date 2020/7/6 22:11
 */
public class RegisterCommand implements CommandExecutor
{
    private final JavaPlugin plugin;

    public RegisterCommand(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player)
        {
            if (strings.length == 1)
            {
                Player player = (Player)commandSender;
                if(LoginManager.isLogin(player.getName()))
                {
                    player.sendMessage("§4你已注册并登录！");
                    return true;
                }
                if (LoginManager.isRegister(player.getName()))
                {
                    player.sendMessage("§4你已经注册，不能重复注册！");
                    player.sendMessage("  §4或已有同名玩家注册，请更换你的用户名！");
                    return true;
                }
                LoginManager.register(player.getName(), strings[0]);
                LoginManager.setUnLoginState(player.getName(), false);
                TitleAPI.sendTitle(player,10,60,10,"§a欢迎进入服务器！", "玩家" + player.getName());
                return true;
            }
            return false;
        }
        commandSender.sendMessage("服务器不能使用register命令！");
        return false;
    }
}
