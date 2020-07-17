package com.new_afterwave.mc.Commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.Util.LoginManager;
import com.new_afterwave.mc.VexView.LoginGui;
import lk.vexview.api.VexViewAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author by
 * @description:
 * @date 2020/7/6 22:10
 */
public class LoginCommand implements CommandExecutor
{
    private final JavaPlugin plugin;

    public LoginCommand(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player)
        {
            if (strings.length == 0)
            {
                Player player = (Player)commandSender;
                if (!LoginManager.isLogin(player.getName()))
                    VexViewAPI.openGui(player, LoginGui.getGui(player));
                else
                    player.sendMessage("§4你已登录，无需再次登录！");
                return true;
            }
             else if (strings.length == 1)
            {
                Player player = (Player)commandSender;
                if (LoginManager.isLogin(player.getName()))
                {
                    player.sendMessage("§4你已登录，无需再次登录！");
                    return true;
                }
                if (!LoginManager.isRegister(player.getName()))
                {
                    player.sendMessage("§4你未注册，请先使用/register命令注册！");
                    return true;
                }
                LoginManager.login(player.getName(), strings[0]);
                TitleAPI.sendTitle(player, 10, 60, 10, "§a欢迎进入服务器！", "玩家" + player.getName());
                return true;
            }
            return false;
        }
        commandSender.sendMessage("服务器不能使用register命令！");
        return false;
    }
}
