package com.new_afterwave.mc.Listener;

import com.connorlinfoot.titleapi.TitleAPI;
import com.new_afterwave.mc.Controller.StateControl;
import com.new_afterwave.mc.MainPlugin;
import com.new_afterwave.mc.Runnable.DefendModule;
import com.new_afterwave.mc.Util.HealthList;
import com.new_afterwave.mc.Util.LoginManager;
import com.new_afterwave.mc.Util.PluginInfo;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.event.gui.VexGuiCloseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


/**
 * @author by
 * @description:
 * @date 2020/7/10 12:50
 */
public class LoginGuiListener implements Listener
{
    @EventHandler
    public void onVexButtonClick(ButtonClickEvent event)
    {
        if (event.getButton().getName().equals("登录") || event.getButton().getName().equals("注册"))
        {
            int buttonID = (int)event.getButtonID();
            Player player = event.getPlayer();
            String password = event.getGui().getTextField(3).getTypedText();
            if (buttonID == 1)
            {
                //login
                if (LoginManager.isLogin(player.getName()))
                {
                    player.sendMessage("-----\n§4你已登录！");
                    return;
                }
                if (!LoginManager.isRegister(player.getName()))
                {
                    player.sendMessage("-----\n§4你未注册！");
                    return;
                }
                if (!LoginManager.login(player.getName(), password))
                {
                    player.sendMessage("-----\n§4密码错误！");
                    return;
                }
                TitleAPI.sendTitle(player, 10, 60, 10, "§a欢迎进入服务器！", "玩家" + player.getName());
                player.closeInventory();
            }
            else if (buttonID == 2)
            {
                if (LoginManager.isLogin(player.getName()))
                {
                    player.sendMessage("-----\n§4你已注册！");
                    return;
                }
                if (LoginManager.isRegister(player.getName()))
                {
                    player.sendMessage("-----\n§4你已注册！");
                    player.sendMessage("§4或存在同名玩家！");
                    player.sendMessage("§4  请更换用户名！");
                    return;
                }
                if (password.equalsIgnoreCase(""))
                {
                    player.sendMessage("-----\n§4密码不得为空！");
                    return;
                }
                LoginManager.register(player.getName(), password);
                LoginManager.setUnLoginState(player.getName(), false);
                TitleAPI.sendTitle(player, 10, 60, 10, "§a欢迎进入服务器！", "玩家" + player.getName());
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onVexGuiClose(VexGuiCloseEvent event)
    {
        try
        {
            if (event.getGui().getButtonById(1).getName().equals("登录"))
            {
                Player player = event.getPlayer();
                if (!LoginManager.isLogin(player.getName()))
                {
                    TitleAPI.sendTitle(player, 10, 60, 10, "请先登录/注册！", "玩家" + player.getName());
                    player.sendMessage("-----\n§4你需要先注册/登录才能进行游戏！");
                }
                else
                {
                    StateControl stateControl = new StateControl((MainPlugin)MainPlugin.plugin);
                    HealthList healthList = PluginInfo.getHealthList(event.getPlayer().getUniqueId());
                    if (healthList.isInfected())
                    {
                        stateControl.infectPlayer(event.getPlayer(), 100);
                    }
                    new DefendModule(player.getUniqueId(), "mask").runTaskTimerAsynchronously(MainPlugin.plugin, 0L, 20L);
                    new DefendModule(player.getUniqueId(), "disinfector").runTaskTimerAsynchronously(MainPlugin.plugin, 0L, 20L);
                }
            }
        }
        catch (NullPointerException e) { }

    }
}
